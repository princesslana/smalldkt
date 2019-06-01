package com.github.princesslana.smalldkt.type.channel.events

import com.github.princesslana.smalldkt.type.ChannelEvent
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import kotlinx.serialization.Serializable

// Channel Pins Update
// https://discordapp.com/developers/docs/topics/gateway#channel-pins-update

@Serializable
data class ChannelPinsUpdateEvent(
    val guild_id: Snowflake? = null,
    val last_pin_timestamp: TimeStamp? = null,
    override val channel_id: Snowflake
) : ChannelEvent