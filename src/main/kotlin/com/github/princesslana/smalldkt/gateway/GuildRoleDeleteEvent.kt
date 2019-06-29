package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class GuildRoleDeleteEvent(
    override val guild_id: Snowflake,
    val role_id: Snowflake
) : GuildEvent