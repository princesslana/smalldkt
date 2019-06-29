package com.github.princesslana.smalldkt.type.guild

import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Integration(
    val id: Snowflake,
    val name: String,
    val type: String,
    val enabled: Boolean,
    val syncing: Boolean,
    val role_id: Snowflake,
    val expire_behavior: Int,
    val expire_grace_period: Int,
    val user: User,
    val account: IntegrationAccount,
    val synced_at: TimeStamp
)

@Serializable
data class PartialIntegration(
    val id: Snowflake? = null,
    val name: String? = null,
    val type: String? = null,
    val enabled: Boolean? = null,
    val syncing: Boolean? = null,
    val role_id: Snowflake? = null,
    val expire_behavior: Int? = null,
    val expire_grace_period: Int? = null,
    val user: User? = null,
    val account: IntegrationAccount? = null,
    val synced_at: TimeStamp? = null
)

@Serializable
data class IntegrationAccount(
    val id: String,
    val name: String
)