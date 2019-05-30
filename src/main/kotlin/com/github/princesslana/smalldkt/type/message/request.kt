package com.github.princesslana.smalldkt.type.message

import com.github.princesslana.smalldkt.ChannelEvent
import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.Snowflake
import com.github.princesslana.smalldkt.post
import com.github.princesslana.smalldkt.type.channel.Channel
import kotlinx.serialization.Serializable

// Create Message TODO support files
// https://discordapp.com/developers/docs/resources/channel#create-message

@Serializable
data class CreateMessagePayload(
    val content: String? = null,
    val nonce: Snowflake? = null,
    val tts: Boolean? = null,
    val embed: Embed? = null
) {
    init {
        require(content != null || embed != null)
    }
}

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, payload: CreateMessagePayload) =
    smallD.post(payload, CreateMessagePayload.serializer(), "/channels/$channelId/messages", Message.serializer())

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, content: String) =
    createMessage(channelId, CreateMessagePayload(content = content))

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, embed: Embed) =
    createMessage(channelId, CreateMessagePayload(embed = embed))

suspend fun SmallDData<out ChannelEvent>.respond(content: String) {
    createMessage(this.event.eventChannel, content)
}

suspend fun SmallDData<out ChannelEvent>.respond(embed: Embed) {
    createMessage(this.event.eventChannel, embed)
}