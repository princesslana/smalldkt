package com.github.princesslana.smalldkt.type.guild

import com.github.princesslana.smalldkt.Snowflake
import com.github.princesslana.smalldkt.TimeStamp
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Integration(
    val id: Snowflake,
    val name: String,
    val type: String,
    val enabled: Boolean,
    val syncing: Boolean,
    @SerialName("role_id") val roleId: Snowflake,
    @SerialName("expire_behavior") val expireBehavior: Int,
    @SerialName("expire_grace_period") val expireGracePeriod: Int,
    val user: User,
    val account: IntegrationAccount,
    @SerialName("synced_at") val syncedAt: TimeStamp
)

@Serializable
data class IntegrationAccount(
    val id: String,
    val name: String
)