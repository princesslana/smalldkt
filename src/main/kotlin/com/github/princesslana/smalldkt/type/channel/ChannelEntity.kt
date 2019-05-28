package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.Optional
import com.github.princesslana.smalldkt.Snowflake
import com.github.princesslana.smalldkt.TimeStamp
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class Channel(
    val id: Snowflake,
    val type: ChannelType,
    val guildId: Snowflake? = null,
    val position: Int? = null,
    @SerialName("permission_overwrites") val permissionOverwrites: List<Overwrite>? = null,
    val name: String? = null,
    val topic: Optional<String>? = Optional.absent(),
    val nsfw: Boolean? = null,
    @SerialName("last_message_id") val lastMessageId: Optional<Snowflake>? = Optional.absent(),
    val bitrate: Int? = null,
    @SerialName("user_limit") val userLimit: Int? = null,
    @SerialName("rate_limit_per_use") val rateLimitPerUse: Int? = null,
    val recipients: List<User>? = null,
    val icon: Optional<String>? = Optional.absent(),
    @SerialName("owner_id") val ownerId: Snowflake? = null,
    @SerialName("application_id") val applicationId: Snowflake? = null,
    @SerialName("parent_id") val parentId: Optional<Snowflake>? = Optional.absent(),
    @SerialName("last_pin_timestamp") val lastPinTimestamp: TimeStamp? = null
)

enum class ChannelType(val value: Int) {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4),
    GUILD_NEWS(5),
    GUILD_STORE(6);

    companion object : KSerializer<ChannelType> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("ChannelTypeSerializer")

        override fun deserialize(decoder: Decoder): ChannelType {
            return when (decoder.decodeInt()) {
                0 -> GUILD_TEXT
                1 -> DM
                2 -> GUILD_VOICE
                3 -> GROUP_DM
                4 -> GUILD_CATEGORY
                5 -> GUILD_NEWS
                6 -> GUILD_STORE
                else -> throw IllegalStateException("Illegal Channel Type.")
            }
        }

        override fun serialize(encoder: Encoder, obj: ChannelType) {
            encoder.encodeInt(obj.value)
        }
    }
}