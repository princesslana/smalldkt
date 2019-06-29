package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.MessageEvent
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class MessageDeleteEvent(
    val id: Snowflake,
    override val channel_id: Snowflake,
    val guild_id: Snowflake? = null
) : MessageEvent {
    override val message_id: Snowflake = id
}