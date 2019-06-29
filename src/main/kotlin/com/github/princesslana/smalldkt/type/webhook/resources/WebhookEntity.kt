package com.github.princesslana.smalldkt.type.webhook.resources

import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Webhook(
    val id: Snowflake,
    val guild_id: Snowflake? = null,
    val channel_id: Snowflake,
    val user: User? = null,
    val name: String?,
    val avatar: String?,
    val token: String
)