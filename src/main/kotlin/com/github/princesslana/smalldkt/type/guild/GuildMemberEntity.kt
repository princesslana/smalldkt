package com.github.princesslana.smalldkt.type.guild

import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

interface GuildMemberFace {
    val user: User
    val nick: String? // optional
    val roles: List<Snowflake>
    val joined_at: TimeStamp
    val premium_since: Optional<TimeStamp>? // nullable
    val deaf: Boolean
    val mute: Boolean
}

@Serializable
data class GuildMember(
    override val user: User,
    override val nick: String? = null,
    override val roles: List<Snowflake>,
    override val joined_at: TimeStamp,
    override val premium_since: Optional<TimeStamp>? = Optional.absent(),
    override val deaf: Boolean,
    override val mute: Boolean
) : GuildMemberFace

@Serializable
data class PartialGuildMember(
    val user: User? = null,
    val nick: String? = null,
    val roles: List<Snowflake>? = null,
    val joined_at: TimeStamp? = null,
    val premium_since: Optional<TimeStamp>? = Optional.absent(),
    val deaf: Boolean? = null,
    val mute: Boolean? = null
)