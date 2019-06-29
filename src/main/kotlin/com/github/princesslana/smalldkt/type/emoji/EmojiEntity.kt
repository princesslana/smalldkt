package com.github.princesslana.smalldkt.type.emoji

import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    val id: Snowflake?,
    val name: String,
    val roles: List<Snowflake>? = null,
    val user: User? = null,
    val require_colons: Boolean? = null,
    val managed: Boolean? = null,
    val animated: Boolean? = null
)

@Serializable
data class PartialEmoji(
    val id: Optional<Snowflake>? = Optional.absent(),
    val name: String? = null,
    val roles: List<Snowflake>? = null,
    val user: User? = null,
    val require_colons: Boolean? = null,
    val managed: Boolean? = null,
    val animated: Boolean? = null
)