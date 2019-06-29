package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalld.Attachment
import com.github.princesslana.smalld.SmallD
import com.github.princesslana.smalldkt.ChannelEvent
import com.github.princesslana.smalldkt.MessageEvent
import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.http.*
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.emoji.Emoji
import com.github.princesslana.smalldkt.type.invite.Invite
import com.github.princesslana.smalldkt.type.invite.InviteWithMetadata
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.UnitSerializer

suspend fun SmallDData<*>.getChannel(channelId: Snowflake): Channel =
    smallD.get("/channels/$channelId", Channel.serializer())

@Serializable
data class ModifyChannelPayload(
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

suspend fun SmallDData<*>.modifyChannel(channelId: Snowflake, payload: ModifyChannelPayload): Channel =
    smallD.patch(
        payload,
        ModifyChannelPayload.serializer(),
        "/channels/$channelId",
        Channel.serializer()
    )

suspend fun SmallDData<*>.deleteChannel(channelId: Snowflake): Channel =
    smallD.delete("/channels/$channelId", Channel.serializer())

class GetChannelMessagesPayload(
    around: Snowflake? = null,
    before: Snowflake? = null,
    after: Snowflake? = null,
    limit: Int = 50
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
    payload: GetChannelMessagesPayload
): List<Channel> =
    smallD.get(
        "/channels/$channelId/messages${payload.queryString}",
        ArrayListSerializer(Channel.serializer())
    )

suspend fun SmallDData<*>.getChannelMessage(channelId: Snowflake, messageId: Snowflake): Channel =
    smallD.get("/channels/$channelId/messages/$messageId", Channel.serializer())

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

suspend fun SmallDData<*>.createMessage(
    channelId: Snowflake,
    createMessageRequest: CreateMessageRequest,
    vararg attachments: Attachment
) = smallD.post(
    createMessageRequest,
    CreateMessageRequest.serializer(),
    "/channels/$channelId/messages",
    Message.serializer(),
    *attachments
)

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, content: String, vararg attachments: Attachment) =
    createMessage(channelId, CreateMessageRequest(content = content), *attachments)

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, embed: Embed, vararg attachments: Attachment) =
    createMessage(channelId, CreateMessageRequest(embed = embed), *attachments)

suspend fun SmallDData<*>.createMessage(channelId: Snowflake, vararg attachments: Attachment) =
    createMessage(channelId, CreateMessageRequest(), *attachments)

suspend fun SmallDData<out ChannelEvent>.respond(content: String, vararg attachments: Attachment) =
    createMessage(event.channel_id, content, *attachments)

suspend fun SmallDData<out ChannelEvent>.respond(embed: Embed, vararg attachments: Attachment) =
    createMessage(event.channel_id, embed, *attachments)

suspend fun SmallDData<out ChannelEvent>.respond(vararg attachments: Attachment) =
    createMessage(event.channel_id, *attachments)

suspend fun SmallDData<*>.createReaction(channelId: Snowflake, messageId: Snowflake, emoji: String) =
    smallD.put(Unit, UnitSerializer, "/channels/$channelId/messages/$messageId/reactions/$emoji/@me", UnitSerializer)

suspend fun SmallDData<out ChannelEvent>.addReaction(messageId: Snowflake, emoji: String) =
    createReaction(event.channel_id, messageId, emoji)

suspend fun SmallDData<out MessageEvent>.react(emoji: String) =
    createReaction(event.channel_id, event.message_id, emoji)

suspend fun SmallDData<*>.deleteReaction(channelId: Snowflake, messageId: Snowflake, emoji: String) =
    smallD.delete("/channels/$channelId/messages/$messageId/reactions/$emoji/@me", UnitSerializer)

suspend fun SmallDData<*>.deleteUserReaction(
    channelId: Snowflake,
    messageId: Snowflake,
    emoji: String,
    userId: Snowflake
) = smallD.delete("/channels/$channelId/messages/$messageId/reactions/$emoji/$userId", UnitSerializer)

suspend fun SmallDData<*>.getReactions(
    channelId: Snowflake,
    messageId: Snowflake,
    emoji: String,
    payload: GetReactionsPayload
) = smallD.get(
    "/channels/$channelId/messages/$messageId/reactions/$emoji${payload.queryString}",
    ArrayListSerializer(User.serializer())
)

class GetReactionsPayload(before: Snowflake? = null, after: Snowflake? = null, limit: Int? = null) {
    val queryString = queryString {
        if (before != null) +("before" to before)
        if (after != null) +("after" to after)
        if (limit != null) +("limit" to limit)
    }
}

suspend fun SmallDData<*>.deleteAllReactions(channelId: Snowflake, messageId: Snowflake) =
    smallD.delete("/channels/$channelId/messages/$messageId/reactions", UnitSerializer)

suspend fun SmallDData<*>.editMessage(channelId: Snowflake, messageId: Snowflake, payload: EditMessagePayload) =
    smallD.patch(
        payload,
        EditMessagePayload.serializer(),
        "/channels/$channelId/messages/$messageId",
        Message.serializer()
    )

suspend fun SmallDData<out ChannelEvent>.edit(messageId: Snowflake, content: String, embed: Embed) =
    editMessage(event.channel_id, messageId, EditMessagePayload(content, embed))

suspend fun SmallDData<out ChannelEvent>.edit(messageId: Snowflake, content: String) =
    editMessage(event.channel_id, messageId, EditMessagePayload(content = content))

suspend fun SmallDData<out ChannelEvent>.edit(messageId: Snowflake, embed: Embed) =
    editMessage(event.channel_id, messageId, EditMessagePayload(embed = embed))

suspend fun SmallDData<out MessageEvent>.edit(content: String, embed: Embed) = edit(event.message_id, content, embed)
suspend fun SmallDData<out MessageEvent>.edit(content: String) = edit(event.message_id, content)
suspend fun SmallDData<out MessageEvent>.edit(embed: Embed) = edit(event.message_id, embed)

@Serializable
data class EditMessagePayload(val content: String? = null, val embed: Embed? = null)

suspend fun SmallDData<*>.deleteMessage(channelId: Snowflake, messageId: Snowflake) =
    smallD.delete("/channels/$channelId/messages/$messageId", UnitSerializer)

suspend fun SmallDData<out ChannelEvent>.deleteMessage(messageId: Snowflake) =
    deleteMessage(event.channel_id, messageId)

suspend fun SmallDData<out MessageEvent>.delete() = deleteMessage(event.message_id)

suspend fun SmallDData<*>.bulkDeleteMessages(channelId: Snowflake, payload: BulkDeleteMessagesPayload) =
    smallD.post(
        payload,
        BulkDeleteMessagesPayload.serializer(),
        "/channels/$channelId/messages/bulk-delete",
        UnitSerializer
    )

suspend fun SmallDData<out ChannelEvent>.bulkDeleteMessages(messages: List<Snowflake>) =
    bulkDeleteMessages(event.channel_id, BulkDeleteMessagesPayload(messages))

@Serializable
data class BulkDeleteMessagesPayload(val messages: List<Snowflake>)

suspend fun SmallDData<*>.editChannelPermissions(
    channelId: Snowflake,
    overwriteId: Snowflake,
    payload: EditChannelPermissionsPayload
) = smallD.put(
    payload,
    EditChannelPermissionsPayload.serializer(),
    "/channels/$channelId/permissions/$overwriteId",
    UnitSerializer
)

@Serializable
data class EditChannelPermissionsPayload(val allow: Int, val deny: Int, val type: String)

suspend fun SmallDData<*>.getChannelInvites(channelId: Snowflake) =
    smallD.get("/channels/$channelId/invites", ArrayListSerializer(InviteWithMetadata.serializer()))

suspend fun SmallDData<*>.createChannelInvite(channelId: Snowflake, payload: CreateChannelInvitePayload) =
    smallD.post(payload, CreateChannelInvitePayload.serializer(), "/channels/$channelId/invites", Invite.serializer())

@Serializable
data class CreateChannelInvitePayload(
    val max_age: Int? = null,
    val max_uses: Int? = null,
    val temporary: Boolean? = null,
    val unique: Boolean? = null
)

suspend fun SmallDData<*>.deleteChannelPermission(channelId: Snowflake, overwriteId: Snowflake) =
    smallD.delete("/channels/$channelId/permissions/$overwriteId", UnitSerializer)

suspend fun SmallDData<*>.triggerTypingIndicator(channelId: Snowflake) =
    smallD.post(Unit, UnitSerializer, "/channels/$channelId/typing", UnitSerializer)

suspend fun SmallDData<*>.getPinnedMessages(channelId: Snowflake) =
    smallD.get("/channels/$channelId/pins", ArrayListSerializer(Message.serializer()))

suspend fun SmallDData<*>.addPinnedChannelMessage(channelId: Snowflake, messageId: Snowflake) =
    smallD.put(Unit, UnitSerializer, "/channels/$channelId/pins/$messageId", UnitSerializer)

suspend fun SmallDData<*>.deletePinnedChannelMessage(channelId: Snowflake, messageId: Snowflake) =
    smallD.delete("/channels/$channelId/pins/$messageId", UnitSerializer)

suspend fun SmallDData<*>.groupDMAddRecipient(
    channelId: Snowflake,
    userId: Snowflake,
    payload: GroupDMAddRecipientPayload
) = smallD.put(
    payload,
    GroupDMAddRecipientPayload.serializer(),
    "/channels/$channelId/recipients/$userId",
    UnitSerializer
)

@Serializable
data class GroupDMAddRecipientPayload(val access_token: String, val nick: String)

suspend fun SmallDData<*>.groupDMRemoveRecipient(channelId: Snowflake, userId: Snowflake) =
    smallD.delete("/channels/$channelId/recipients/$userId", UnitSerializer)