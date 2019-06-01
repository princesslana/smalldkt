package com.github.princesslana.smalldkt.type.channel.resources

import com.github.princesslana.smalldkt.type.ChannelContainer
import com.github.princesslana.smalldkt.type.Identifiable
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.guild.resources.PartialGuildMember
import com.github.princesslana.smalldkt.type.user.user.User
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable(with = MessageImpl.`$serializer`::class)
interface Message : Identifiable, ChannelContainer {
    val guild_id: Snowflake?
    val author: User
    val member: PartialGuildMember?
    val content: String
    val timestamp: TimeStamp
    val edited_timestamp: TimeStamp?
    val tts: Boolean
    val mention_everyone: Boolean
    val mentions: List<User>
    val mention_roles: List<Snowflake>
    val attachments: List<MessageAttachment>
    val embeds: List<Embed>
    val reactions: List<Reaction>?
    val nonce: Optional<Snowflake>?
    val pinned: Boolean
    val webhook_id: Snowflake?
    val type: MessageType
    val activity: MessageActivity?
    val application: MessageApplication?
}

@Serializable
data class MessageImpl(
    override val guild_id: Snowflake? = null,
    override val author: User,
    override val member: PartialGuildMember? = null,
    override val content: String,
    override val timestamp: TimeStamp,
    override val edited_timestamp: TimeStamp?,
    override val tts: Boolean,
    override val mention_everyone: Boolean,
    override val mentions: List<User>,
    override val mention_roles: List<Snowflake>,
    override val attachments: List<MessageAttachment>,
    override val embeds: List<Embed>,
    override val reactions: List<Reaction>? = null,
    override val nonce: Optional<Snowflake>? = Optional.absent(),
    override val pinned: Boolean,
    override val webhook_id: Snowflake? = null,
    override val type: MessageType,
    override val activity: MessageActivity? = null,
    override val application: MessageApplication? = null,
    override val id: Snowflake,
    override val channel_id: Snowflake
) : Message

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

@Serializable(with = MessageActivityImpl.`$serializer`::class)
interface MessageActivity {
    val type: MessageActivityType
    val party_id: String?
}

@Serializable
data class MessageActivityImpl(override val type: MessageActivityType, override val party_id: String? = null) :
    MessageActivity

@Serializable(with = MessageApplicationImpl.`$serializer`::class)
interface MessageApplication : Identifiable {
    val cover_image: String?
    val description: String
    val icon: String?
    val name: String
}

@Serializable
data class MessageApplicationImpl(
    override val cover_image: String? = null,
    override val description: String,
    override val icon: String?,
    override val name: String,
    override val id: Snowflake
) : MessageApplication

@Serializable(with = MessageActivityTypeSerializer::class)
enum class MessageActivityType(val value: Int) {
    JOIN(1),
    SPECTATE(2),
    LISTEN(3),
    JOIN_REQUEST(5)
}

@Serializer(forClass = MessageActivityType::class)
class MessageActivityTypeSerializer : KSerializer<MessageActivityType> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("MessageActivityTypeSerializer")

    override fun deserialize(decoder: Decoder): MessageActivityType {
        return when (decoder.decodeInt()) {
            1 -> MessageActivityType.JOIN
            2 -> MessageActivityType.SPECTATE
            3 -> MessageActivityType.LISTEN
            5 -> MessageActivityType.JOIN_REQUEST
            else -> throw IllegalStateException("Illegal Message Activity Type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: MessageActivityType) {
        encoder.encodeInt(obj.value)
    }
}