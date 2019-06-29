package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.guild.PartialGuildMember
import com.github.princesslana.smalldkt.type.user.PartialGuildUser
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

interface MessageFace {
    val id: Snowflake
    val channel_id: Snowflake
    val guild_id: Snowflake? // optional
    val author: User
    val member: PartialGuildMember? // optional
    val content: String
    val timestamp: TimeStamp
    val edited_timestamp: TimeStamp? // nullable
    val tts: Boolean
    val mention_everyone: Boolean
    val mentions: List<PartialGuildUser>
    val mention_roles: List<Snowflake>
    val attachments: List<MessageAttachment>
    val embeds: List<Embed>
    val reactions: List<Reaction>? // optional
    val nonce: Optional<Snowflake>?
    val pinned: Boolean
    val webhook_id: Snowflake? // optional
    val type: MessageType
    val activity: MessageActivity? // optional
    val application: MessageApplication? // optional
}

@Serializable
data class Message(
    override val id: Snowflake,
    override val channel_id: Snowflake,
    override val guild_id: Snowflake? = null,
    override val author: User,
    override val member: PartialGuildMember? = null,
    override val content: String,
    override val timestamp: TimeStamp,
    override val edited_timestamp: TimeStamp?,
    override val tts: Boolean,
    override val mention_everyone: Boolean,
    override val mentions: List<PartialGuildUser>,
    override val mention_roles: List<Snowflake>,
    override val attachments: List<MessageAttachment>,
    override val embeds: List<Embed>,
    override val reactions: List<Reaction>? = null,
    override val nonce: Optional<Snowflake>? = Optional.absent(),
    override val pinned: Boolean,
    override val webhook_id: Snowflake? = null,
    override val type: MessageType,
    override val activity: MessageActivity? = null,
    override val application: MessageApplication? = null
) : MessageFace

@Serializable
data class PartialMessage(
    val id: Snowflake? = null,
    val channel_id: Snowflake? = null,
    val guild_id: Snowflake? = null,
    val author: User? = null,
    val member: PartialGuildMember? = null,
    val content: String? = null,
    val timestamp: TimeStamp? = null,
    val edited_timestamp: Optional<TimeStamp>? = Optional.absent(),
    val tts: Boolean? = null,
    val mention_everyone: Boolean? = null,
    val mentions: List<PartialGuildUser>? = null,
    val mention_roles: List<Snowflake>? = null,
    val attachments: List<MessageAttachment>? = null,
    val embeds: List<Embed>? = null,
    val reactions: List<Reaction>? = null,
    val nonce: Optional<Snowflake>? = Optional.absent(),
    val pinned: Boolean? = null,
    val webhook_id: Snowflake? = null,
    val type: MessageType? = null,
    val activity: MessageActivity? = null,
    val application: MessageApplication? = null
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
    GUILD_MEMBER_JOIN(7),
    USER_PREMIUM_GUILD_SUBSCRIPTION(8),
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_1(9),
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_2(10),
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_3(11)
}

@Serializer(forClass = MessageType::class)
class MessageTypeSerializer : KSerializer<MessageType> {
    override val descriptor = StringDescriptor.withName("MessageTypeSerializer")

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
            8 -> MessageType.USER_PREMIUM_GUILD_SUBSCRIPTION
            9 -> MessageType.USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_1
            10 -> MessageType.USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_2
            11 -> MessageType.USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_3
            else -> throw IllegalStateException("Illegal value passed for message type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: MessageType) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable
data class MessageActivity(
    val type: MessageActivityType,
    val party_id: String? = null
)

@Serializable(with = MessageActivityTypeSerializer::class)
enum class MessageActivityType(val value: Int) {
    JOIN(1),
    SPECTATE(2),
    LISTEN(3),
    JOIN_REQUEST(5)
}

@Serializer(forClass = MessageActivityType::class)
class MessageActivityTypeSerializer : KSerializer<MessageActivityType> {
    override val descriptor = StringDescriptor.withName("MessageActivityTypeSerializer")

    override fun deserialize(decoder: Decoder): MessageActivityType {
        return when (decoder.decodeInt()) {
            1 -> MessageActivityType.JOIN
            2 -> MessageActivityType.SPECTATE
            3 -> MessageActivityType.LISTEN
            5 -> MessageActivityType.JOIN_REQUEST
            else -> throw IllegalStateException("Illegal value passed for message activity type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: MessageActivityType) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable
data class MessageApplication(
    val id: Snowflake,
    val cover_image: String? = null,
    val description: String,
    val icon: String?,
    val name: String
)