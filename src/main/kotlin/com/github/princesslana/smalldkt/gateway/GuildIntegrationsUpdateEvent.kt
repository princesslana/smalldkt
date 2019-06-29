package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class GuildIntegrationsUpdateEvent(
    override val guild_id: Snowflake
) : GuildEvent