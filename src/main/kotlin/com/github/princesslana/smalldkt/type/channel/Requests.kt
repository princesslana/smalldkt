package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.http.*
import com.github.princesslana.smalldkt.http.delete
import com.github.princesslana.smalldkt.http.get
import com.github.princesslana.smalldkt.http.patch
import com.github.princesslana.smalldkt.type.ChannelEvent
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.channel.resources.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.ArrayListSerializer

suspend fun SmallDData<*>.getChannel(channelId: Snowflake): Channel =
    smallD.get("/channels/$channelId", ChannelImpl.serializer())

@Serializable
data class ModifyChannelRequest(
    val name: String? = null,
    val position: Int? = null,
    val topic: String? = null,
    val nsfw: Boolean? = null,
    val rate_limit_per_user: Int? = null,
    val bitrate: Int? = null,
    val user_limit: Int? = null,
    val permission_overwrites: List<Overwrite>? = null,
    val parent_id: Snowflake? = null
)

suspend fun SmallDData<*>.modifyChannel(channelId: Snowflake, modifyChannelRequest: ModifyChannelRequest): Channel =
    smallD.patch(
        modifyChannelRequest,
        ModifyChannelRequest.serializer(),
        "/channels/$channelId",
        ChannelImpl.serializer()
    )

suspend fun SmallDData<*>.deleteChannel(channelId: Snowflake): Channel =
    smallD.delete("/channels/$channelId", ChannelImpl.serializer())

data class GetChannelMessagesRequest(
    val around: Snowflake? = null,
    val before: Snowflake? = null,
    val after: Snowflake? = null,
    val limit: Int = 50
) {
    init {
        require(limit in 1..100)
        var count = 0
        if (around != null) count++
        if (before != null) count++
        if (after != null) count++
        if (count > 1) throw IllegalStateException("Please select around, before, or after. Only one may be passed at a time.")
    }

    val queryString = queryString {
        if (around != null) +("around" to "$around")
        if (before != null) +("before" to "$before")
        if (after != null) +("after" to "$after")
        +("limit" to "$limit")
    }
}

suspend fun SmallDData<*>.getChannelMessages(
    channelId: Snowflake,
    getChannelMessagesRequest: GetChannelMessagesRequest
): List<Channel> =
    smallD.get(
        "/channels/$channelId/messages/${getChannelMessagesRequest.queryString}",
        ArrayListSerializer(ChannelImpl.serializer())
    )

suspend fun SmallDData<*>.getChannelMessage(channelId: Snowflake, messageId: Snowflake): Channel =
    smallD.get("/channels/$channelId/messages/$messageId", ChannelImpl.serializer())

// TODO implement file sending
@Serializable
data class CreateMessageRequest(
    val content: String? = null,
    val nonce: Snowflake? = null,
    val tts: Boolean? = null,
    val embed: Embed? = null
) {
    init {
        require(content != null || embed != null)
    }
}

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, createMessageRequest: CreateMessageRequest) =
    smallD.post(
        createMessageRequest,
        CreateMessageRequest.serializer(),
        "/channels/$channelId/messages",
        MessageImpl.serializer()
    )

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, content: String) =
    createMessage(channelId, CreateMessageRequest(content = content))

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, embed: Embed) =
    createMessage(channelId, CreateMessageRequest(embed = embed))

suspend fun SmallDData<out ChannelEvent>.respond(content: String) {
    createMessage(this.event.channel_id, content)
}

suspend fun SmallDData<out ChannelEvent>.respond(embed: Embed) {
    createMessage(this.event.channel_id, embed)
}