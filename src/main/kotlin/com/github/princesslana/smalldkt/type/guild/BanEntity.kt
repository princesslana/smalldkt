package com.github.princesslana.smalldkt.type.guild

import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Ban(
    val reason: String?,
    val user: User
)