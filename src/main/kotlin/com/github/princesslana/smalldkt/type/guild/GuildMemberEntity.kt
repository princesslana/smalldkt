package com.github.princesslana.smalldkt.type.guild

import com.github.princesslana.smalldkt.Snowflake
import com.github.princesslana.smalldkt.TimeStamp
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildMember(
    val user: User,
    val nick: String? = null,
    val roles: List<Snowflake>,
    @SerialName("joined_at") val joinedAt: TimeStamp,
    val deaf: Boolean,
    val mute: Boolean
)

@Serializable
data class PartialGuildMember(
    val nick: String? = null,
    val roles: List<Snowflake>,
    @SerialName("joined_at") val joinedAt: TimeStamp,
    val deaf: Boolean,
    val mute: Boolean
)