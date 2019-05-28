package com.github.princesslana.smalldkt.type.guild

import kotlinx.serialization.Serializable

@Serializable
data class PartialGuild(
    val id: String,
    val name: String,
    val icon: String?,
    val owner: Boolean? = null,
    val permissions: Int? = null
)