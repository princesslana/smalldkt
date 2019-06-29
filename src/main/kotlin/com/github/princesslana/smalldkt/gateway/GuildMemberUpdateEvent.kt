package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberUpdateEvent(
    override val guild_id: Snowflake,
    val roles: List<Snowflake>,
    val user: User,
    val nick: String?
) : GuildEvent