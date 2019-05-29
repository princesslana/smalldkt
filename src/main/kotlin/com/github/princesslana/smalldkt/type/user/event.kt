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
    val user: User,
    val roles: List<Snowflake>,
    val game: Activity?,
    @SerialName("guild_id") val guildId: Snowflake,
    val status: String,
    val activities: List<Activity>,
    @SerialName("client_status") val clientStatus: ClientStatus
) : Event

// https://discordapp.com/developers/docs/topics/gateway#user-update
@Serializable(with = WrapperSerializer::class)
class UserUpdateEvent(user: User) : Wrapper<User>(user), Event