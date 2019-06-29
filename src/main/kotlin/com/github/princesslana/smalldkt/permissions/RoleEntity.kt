package com.github.princesslana.smalldkt.permissions

import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class Role(
    val id: Snowflake,
    val name: String,
    val color: Int,
    val hoist: Boolean,
    val position: Int,
    val permissions: PermissionBase,
    val managed: Boolean,
    val mentionable: Boolean
)