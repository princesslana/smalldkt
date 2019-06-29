package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

interface ChannelFace {
    val id: Snowflake
    val type: ChannelType
    val guild_id: Snowflake? // optional
    val position: Int? // optional
    val permission_overwrites: List<Overwrite>? // optional
    val name: String? // optional
    val topic: Optional<String>?
    val nsfw: Boolean? // optional
    val last_message_id: Optional<Snowflake>?
    val bitrate: Int? // optional
    val user_limit: Int? // optional
    val rate_limit_per_use: Int? // optional
    val recipients: List<User>? // optional
    val icon: Optional<String>?
    val owner_id: Snowflake? // optional
    val application_id: Snowflake? // optional
    val parent_id: Optional<Snowflake>?
    val last_pin_timestamp: TimeStamp? // optional
}

@Serializable
data class Channel(
    override val id: Snowflake,
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
    override val rate_limit_per_use: Int? = null,
    override val recipients: List<User>? = null,
    override val icon: Optional<String>? = Optional.absent(),
    override val owner_id: Snowflake? = null,
    override val application_id: Snowflake? = null,
    override val parent_id: Optional<Snowflake>? = Optional.absent(),
    override val last_pin_timestamp: TimeStamp? = null
) : ChannelFace

@Serializable
data class PartialChannel(
    val id: Snowflake? = null,
    val type: ChannelType? = null,
    val guild_id: Snowflake? = null,
    val position: Int? = null,
    val permission_overwrites: List<Overwrite>? = null,
    val name: String? = null,
    val topic: Optional<String>? = Optional.absent(),
    val nsfw: Boolean? = null,
    val last_message_id: Optional<Snowflake>? = Optional.absent(),
    val bitrate: Int? = null,
    val user_limit: Int? = null,
    val rate_limit_per_use: Int? = null,
    val recipients: List<User>? = null,
    val icon: Optional<String>? = Optional.absent(),
    val owner_id: Snowflake? = null,
    val application_id: Snowflake? = null,
    val parent_id: Optional<Snowflake>? = Optional.absent(),
    val last_pin_timestamp: TimeStamp? = null
)

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
    override val descriptor = StringDescriptor.withName("ChannelTypeSerializer")

    override fun deserialize(decoder: Decoder): ChannelType {
        return when (decoder.decodeInt()) {
            0 -> ChannelType.GUILD_TEXT
            1 -> ChannelType.DM
            2 -> ChannelType.GUILD_VOICE
            3 -> ChannelType.GROUP_DM
            4 -> ChannelType.GUILD_CATEGORY
            5 -> ChannelType.GUILD_NEWS
            6 -> ChannelType.GUILD_STORE
            else -> throw IllegalStateException("Illegal value passed for channel type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: ChannelType) {
        encoder.encodeInt(obj.value)
    }
}