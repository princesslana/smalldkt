package com.github.princesslana.smalldkt.type.emoji.resources

import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.user.user.User
import kotlinx.serialization.Serializable

@Serializable(with = EmojiImpl.`$serializer`::class)
interface Emoji {
    val id: Snowflake?
    val name: String
    val roles: List<Snowflake>?
    val user: User?
    val require_colons: Boolean?
    val managed: Boolean?
    val animated: Boolean?
}

@Serializable
data class EmojiImpl(
    override val id: Snowflake?,
    override val name: String,
    override val roles: List<Snowflake>? = null,
    override val user: User? = null,
    override val require_colons: Boolean? = null,
    override val managed: Boolean? = null,
    override val animated: Boolean? = null
) : Emoji