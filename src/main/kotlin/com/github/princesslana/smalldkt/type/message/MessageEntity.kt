package com.github.princesslana.smalldkt.type.message

import com.github.princesslana.smalldkt.Optional
import com.github.princesslana.smalldkt.Snowflake
import com.github.princesslana.smalldkt.TimeStamp
import com.github.princesslana.smalldkt.type.guild.GuildMember
import com.github.princesslana.smalldkt.type.guild.PartialGuildMember
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class Message(
    val id: Snowflake,
    @SerialName("channel_id") val channelId: Snowflake,
    @SerialName("guild_id") val guildId: Snowflake? = null,
    val author: User,
    val member: PartialGuildMember? = null,
    val content: String,
    val timestamp: TimeStamp,
    @SerialName("edited_timestamp") val editedTimestamp: TimeStamp?,
    val tts: Boolean,
    @SerialName("mention_everyone") val mentionEveryone: Boolean,
    val mentions: List<User>,
    @SerialName("mention_roles") val mentionRoles: List<Snowflake>,
    val attachments: List<MessageAttachment>,
    val embeds: List<Embed>,
    val reactions: List<Reaction>? = null,
    val nonce: Optional<Snowflake>? = Optional.absent(),
    val pinned: Boolean,
    @SerialName("webhook_id") val webhookId: Snowflake? = null,
    val type: MessageType,
    val activity: MessageActivity? = null,
    val application: MessageApplication? = null
)

@Serializable
data class MessageAttachment(
    val id: Snowflake,
    val filename: String,
    val size: Int,
    val url: String,
    @SerialName("proxy_url") val proxyURL: String,
    val height: Int?,
    val width: Int?
)

@Serializable(with = MessageTypeSerializer::class)
enum class MessageType(val value: Int) {
    DEFAULT(0),
    RECIPIENT_ADD(1),
    RECIPIENT_REMOVE(2),
    CALL(3),
    CHANNEL_NAME_CHANGE(4),
    CHANNEL_ICON_CHANGE(5),
    CHANNEL_PINNED_MESSAGE(6),
    GUILD_MEMBER_JOIN(7)
}

@Serializer(forClass = MessageType::class)
class MessageTypeSerializer : KSerializer<MessageType> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("MessageTypeSerializer")

    override fun deserialize(decoder: Decoder): MessageType {
        return when (decoder.decodeInt()) {
            0 -> MessageType.DEFAULT
            1 -> MessageType.RECIPIENT_ADD
            2 -> MessageType.RECIPIENT_REMOVE
            3 -> MessageType.CALL
            4 -> MessageType.CHANNEL_NAME_CHANGE
            5 -> MessageType.CHANNEL_ICON_CHANGE
            6 -> MessageType.CHANNEL_PINNED_MESSAGE
            7 -> MessageType.GUILD_MEMBER_JOIN
            else -> throw IllegalStateException("Illegal Message Type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: MessageType) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable
data class MessageActivity(
    val type: MessageActivityType,
    @SerialName("party_id") val partyId: String? = null
)

@Serializable
enum class MessageActivityType(val value: Int) {
    JOIN(1),
    SPECTATE(2),
    LISTEN(3),
    JOIN_REQUEST(5);

    @Serializer(forClass = MessageActivityType::class)
    companion object : KSerializer<MessageActivityType> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("MessageActivityTypeSerializer")

        override fun deserialize(decoder: Decoder): MessageActivityType {
            return when (decoder.decodeInt()) {
                1 -> JOIN
                2 -> SPECTATE
                3 -> LISTEN
                5 -> JOIN_REQUEST
                else -> throw IllegalStateException("Illegal Message Activity Type.")
            }
        }

        override fun serialize(encoder: Encoder, obj: MessageActivityType) {
            encoder.encodeInt(obj.value)
        }
    }
}

@Serializable
data class MessageApplication(
    val id: Snowflake,
    @SerialName("cover_image") val coverImage: String? = null,
    val description: String,
    val icon: String?,
    val name: String
)