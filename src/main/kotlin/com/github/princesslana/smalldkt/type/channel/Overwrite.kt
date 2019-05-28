package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class Overwrite(
    val id: Snowflake,
    val type: String,
    val allow: Int,
    val deny: Int
)