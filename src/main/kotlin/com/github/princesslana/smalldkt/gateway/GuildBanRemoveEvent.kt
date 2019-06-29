package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

@Serializable
data class GuildBanRemoveEvent(
    override val guild_id: Snowflake,
    val user: User
) : GuildEvent