package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.guild.GuildMemberFace
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberAddEvent(
    override val user: User,
    override val nick: String? = null,
    override val roles: List<Snowflake>,
    override val joined_at: TimeStamp,
    override val premium_since: Optional<TimeStamp>? = Optional.absent(),
    override val deaf: Boolean,
    override val mute: Boolean,
    override val guild_id: Snowflake
) : GuildMemberFace, GuildEvent