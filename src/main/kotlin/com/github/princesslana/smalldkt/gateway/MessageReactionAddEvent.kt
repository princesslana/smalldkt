package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.MessageEvent
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class MessageReactionAddEvent(
    val user_id: Snowflake,
    override val channel_id: Snowflake,
    override val message_id: Snowflake
): MessageEvent