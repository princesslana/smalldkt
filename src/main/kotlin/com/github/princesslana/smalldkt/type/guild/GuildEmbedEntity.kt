package com.github.princesslana.smalldkt.type.guild

import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class GuildEmbed(
    val enabled: Boolean,
    val channel_id: Snowflake?
)