package com.github.princesslana.smalldkt.type.channel.resources

import com.github.princesslana.smalldkt.type.Identifiable
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.user.user.User
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable(with = ChannelImpl.`$serializer`::class)
interface Channel : Identifiable {
    val type: ChannelType
    val guild_id: Snowflake?
    val position: Int?
    val permission_overwrites: List<Overwrite>?
    val name: String?
    val topic: Optional<String>?
    val nsfw: Boolean?
    val last_message_id: Optional<Snowflake>?
    val bitrate: Int?
    val user_limit: Int?
    val rate_limit_per_user: Int?
    val recipients: List<User>?
    val icon: Optional<String>?
    val owner_id: Snowflake?
    val application_id: Snowflake?
    val parent_id: Optional<Snowflake>?
    val last_pin_timestamp: TimeStamp?
}

@Serializable
data class ChannelImpl(
    override val type: ChannelType,
    override val guild_id: Snowflake? = null,
    override val position: Int? = null,
    override val permission_overwrites: List<Overwrite>? = null,
    override val name: String? = null,
    override val topic: Optional<String>? = Optional.absent(),
    override val nsfw: Boolean? = null,
    override val last_message_id: Optional<Snowflake>? = Optional.absent(),
    override val bitrate: Int? = null,
    override val user_limit: Int? = null,
    override val rate_limit_per_user: Int? = null,
    override val recipients: List<User>? = null,
    override val icon: Optional<String>? = Optional.absent(),
    override val owner_id: Snowflake? = null,
    override val application_id: Snowflake? = null,
    override val parent_id: Optional<Snowflake>? = Optional.absent(),
    override val last_pin_timestamp: TimeStamp? = null,
    override val id: Snowflake
) : Channel

@Serializable(with = ChannelTypeSerializer::class)
enum class ChannelType(val value: Int) {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4),
    GUILD_NEWS(5),
    GUILD_STORE(6)
}

@Serializer(forClass = ChannelType::class)
class ChannelTypeSerializer : KSerializer<ChannelType> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("ChannelTypeSerializer")

    override fun deserialize(decoder: Decoder): ChannelType {
        return when (decoder.decodeInt()) {
            0 -> ChannelType.GUILD_TEXT
            1 -> ChannelType.DM
            2 -> ChannelType.GUILD_VOICE
            3 -> ChannelType.GROUP_DM
            4 -> ChannelType.GUILD_CATEGORY
            5 -> ChannelType.GUILD_NEWS
            6 -> ChannelType.GUILD_STORE
            else -> throw IllegalStateException("Illegal Channel Type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: ChannelType) {
        encoder.encodeInt(obj.value)
    }
}