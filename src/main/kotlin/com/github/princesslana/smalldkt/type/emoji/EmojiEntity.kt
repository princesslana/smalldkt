package com.github.princesslana.smalldkt.type.emoji

import com.github.princesslana.smalldkt.Snowflake
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    val id: Snowflake?,
    val name: String,
    val roles: List<Snowflake>? = null,
    val user: User? = null,
    @SerialName("require_colons") val requireColons: Boolean? = null,
    val managed: Boolean? = null,
    val animated: Boolean? = null
)