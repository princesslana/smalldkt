package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.ChannelEvent
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import kotlinx.serialization.Serializable

@Serializable
data class ChannelPinsUpdateEvent(
    val guild_id: Snowflake? = null,
    override val channel_id: Snowflake,
    val last_pin_timestamp: TimeStamp? = null
) : ChannelEvent