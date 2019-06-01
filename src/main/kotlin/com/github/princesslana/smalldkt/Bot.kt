@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.Config
import com.github.princesslana.smalld.SmallD
import com.github.princesslana.smalldkt.type.Event
import com.github.princesslana.smalldkt.type.channel.events.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonObject
import java.lang.Exception

val JSON = Json(JsonConfiguration(encodeDefaults = false, strictMode = false))
const val DEFAULT_CHANNEL_BUFFER = 25

data class BotBuilder(
    val configBuilder: Config.Builder,
    internal val flowContracts: MutableList<suspend (Flow<SmallDData<Event>>) -> Unit>
) {
    fun openContract(flowContract: suspend (Flow<SmallDData<Event>>) -> Unit) {
        flowContracts.add(flowContract)
    }

    inline fun <reified T> subscribe(noinline eventConsumer: suspend SmallDData<T>.() -> Unit) where T : Event, T : Any {
        openContract { it.collectEvent<T> { eventConsumer() } }
    }
}

suspend inline fun <reified T> Flow<SmallDData<Event>>.collectEvent(noinline eventConsumer: suspend SmallDData<T>.() -> Unit) where T : Event, T : Any {
    this.filter { it.event is T }.map {
        @Suppress("UNCHECKED_CAST")
        it as SmallDData<T>
    }.collect(eventConsumer)
}

class Bot(config: Config, flowContracts: List<suspend (Flow<SmallDData<Event>>) -> Unit>) {
    private val channel: BroadcastChannel<Event> = BroadcastChannel(DEFAULT_CHANNEL_BUFFER)
    private val emitterScope = CoroutineScope(Dispatchers.Default)

    init {
        GlobalScope
        val contractScope = CoroutineScope(Dispatchers.Default)
        val smallD = SmallD.create(config)
        smallD.use {
            flowContracts.forEach {
                contractScope.launch {
                    it(channel.asFlow().map { event -> SmallDData(smallD, event) })
                }
            }
            smallD.onGatewayPayload { payload ->
                val element = JSON.parseJson(payload) as JsonObject
                val op = element.getPrimitive("op")
                if (op.int == 0) {
                    val serializer: KSerializer<out Event> = when (val eventName = element.getPrimitive("t").content) {
                        "CHANNEL_CREATE" -> ChannelCreateEvent.serializer()
                        "CHANNEL_UPDATE" -> ChannelUpdateEvent.serializer()
                        "CHANNEL_DELETE" -> ChannelDeleteEvent.serializer()
                        "CHANNEL_PINS_UPDATE" -> ChannelPinsUpdateEvent.serializer()
                        "MESSAGE_CREATE" -> MessageCreateEvent.serializer()
                        "MESSAGE_UPDATE" -> MessageUpdateEvent.serializer()
                        "MESSAGE_DELETE" -> MessageDeleteEvent.serializer()
                        "MESSAGE_DELETE_BULK" -> MessageDeleteBulkEvent.serializer()
                        "MESSAGE_REACTION_ADD" -> MessageReactionAddEvent.serializer()
                        "MESSAGE_REACTION_REMOVE" -> MessageReactionRemoveEvent.serializer()
                        "MESSAGE_REACTION_REMOVE_ALL" -> MessageReactionRemoveAllEvent.serializer()
                        else -> {
                            println("Unhandled event name $eventName")
                            null
                        }
                    } ?: return@onGatewayPayload
                    val event = JSON.fromJson(serializer, element.getObject("d"))
                    emitEvent(event)
                }
            }
            it.run()
        }
    }

    private fun emitEvent(event: Event) {
        emitterScope.launch {
            channel.send(event)
        }
    }
}

data class SmallDData<T>(internal val smallD: SmallD, val event: T) where T : Any, T : Event

fun bot(token: String, botConsumer: BotBuilder.() -> Unit): Bot {
    val config = Config.builder().setToken(token)
    val bot = BotBuilder(config, mutableListOf()).also(botConsumer)
    return Bot(bot.configBuilder.build(), bot.flowContracts)
}