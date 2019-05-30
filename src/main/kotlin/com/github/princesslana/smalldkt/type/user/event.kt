package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.*
import com.github.princesslana.smalldkt.type.gateway.Activity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://discordapp.com/developers/docs/topics/gateway#typing-start
@Serializable
data class TypingStartEvent(
    @SerialName("channel_id") val channelId: Snowflake,
    @SerialName("guild_id") val guildId: Snowflake? = null,
    @SerialName("user_id") val userId: Snowflake,
    val timestamp: TimeStamp
) : Event

// https://discordapp.com/developers/docs/topics/gateway#presence-update
@Serializable
data class ClientStatus(
    val desktop: String? = null,
    val mobile: String? = null,
    val web: String? = null
)

@Serializable
data class PresenceUpdateEvent(
    val user: PresenceUser? = null,
    val roles: List<Snowflake>? = null,
    val game: Optional<Activity>? = Optional.absent(),
    @SerialName("guild_id") val guildId: Snowflake? = null,
    val status: String? = null,
    val activities: List<Activity>? = null,
    @SerialName("client_status") val clientStatus: ClientStatus? = null
) : Event

// https://discordapp.com/developers/docs/topics/gateway#user-update
@Serializable
class UserUpdateEvent(
    val id: Snowflake,
    val username: String,
    val discriminator: String,
    val avatar: String?,
    val bot: Boolean? = null,
    @SerialName("mfa_enabled") val mfaEnabled: Boolean? = null,
    val locale: String? = null,
    val verified: String? = null,
    val email: String? = null,
    val flags: Flag? = null,
    @SerialName("premium_type") val premiumType: PremiumType? = null
) : Event {
    fun isBot() = bot == true
}