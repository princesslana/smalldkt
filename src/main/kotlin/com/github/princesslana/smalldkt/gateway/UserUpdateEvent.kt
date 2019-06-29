package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.Event
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.user.PremiumType
import com.github.princesslana.smalldkt.type.user.UserFace
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateEvent(
    override val id: Snowflake,
    override val username: String,
    override val discriminator: String,
    override val avatar: String?,
    override val bot: Boolean? = null,
    override val mfa_enabled: Boolean? = null,
    override val locale: String? = null,
    override val verified: Boolean? = null,
    override val email: String? = null,
    override val flags: Long? = null,
    override val premium_type: PremiumType? = null
) : UserFace, Event