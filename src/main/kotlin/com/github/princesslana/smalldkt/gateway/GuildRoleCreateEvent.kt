package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.permissions.Role
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class GuildRoleCreateEvent(
    override val guild_id: Snowflake,
    val role: Role
) : GuildEvent