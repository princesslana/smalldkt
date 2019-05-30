@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.Config
import com.github.princesslana.smalld.SmallD
import com.github.princesslana.smalldkt.type.user.PresenceUpdateEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonObject

data class BotBuilder(
    val configBuilder: Config.Builder,
    internal val flowContracts: MutableList<suspend (SmallDData, Flow<Event>) -> Unit>
) {
    fun openContract(flowContract: suspend (SmallDData, Flow<Event>) -> Unit) {
        flowContracts.add(flowContract)
    }

    inline fun <reified T> subscribeEvent(noinline eventConsumer: suspend (SmallDData, T) -> Unit) where T : Event, T : Any {
        openContract { smallDData, flow ->
            flow.collectEvent<T> {
                eventConsumer(smallDData, it)
            }
        }
    }
}

suspend inline fun <reified T> Flow<Event>.collectEvent(noinline eventConsumer: suspend (T) -> Unit) where T : Event, T : Any {
    this.filter { it is T }.map { it as T }.collect(eventConsumer)
}

class Bot(config: Config, flowContracts: List<suspend (SmallDData, Flow<Event>) -> Unit>) {
    val smallDData: SmallDData
    private val channel: BroadcastChannel<Event> = BroadcastChannel(DEFAULT_CHANNEL_BUFFER)
    private val emitterScope = CoroutineScope(Dispatchers.Default)

    init {
        GlobalScope
        val contractScope = CoroutineScope(Dispatchers.Default)
        val smallD = SmallD.create(config)
        smallDData = SmallDData((smallD))
        smallD.use {
            flowContracts.forEach {
                contractScope.launch {
                    it(smallDData, channel.asFlow())
                }
            }
            smallD.onGatewayPayload { payload ->
                val element = JSON.parseJson(payload) as JsonObject
                val op = element.getPrimitive("op")
                if (op.int == 0) {
                    val serializer: KSerializer<out Event> = when (val eventName = element.getPrimitive("t").content) {
                        "PRESENCE_UPDATE" -> PresenceUpdateEvent.serializer()
                        else -> {
                            println("Unhandled event name $eventName")
                            null
                        }
                    } ?: return@onGatewayPayload
                    emitEvent(JSON.fromJson(serializer, element.getObject("d")))
                }
            }
            smallD.onClose {
                channel.close()
            }
            it.run()
        }
    }

    private fun emitEvent(event: Event) = emitterScope.launch {
        channel.send(event)
    }
}

fun bot(token: String, botConsumer: BotBuilder.() -> Unit): Bot {
    val config = Config.builder().setToken(token)
    val bot = BotBuilder(config, mutableListOf()).also(botConsumer)
    return Bot(bot.configBuilder.build(), bot.flowContracts)
}