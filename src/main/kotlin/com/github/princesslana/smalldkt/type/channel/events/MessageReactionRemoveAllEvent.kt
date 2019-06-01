package com.github.princesslana.smalldkt.type.channel.events

import com.github.princesslana.smalldkt.type.ChannelEvent
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

// Message Reaction Remove All
// https://discordapp.com/developers/docs/topics/gateway#message-reaction-remove-all

@Serializable
data class MessageReactionRemoveAllEvent(
    val message_id: Snowflake,
    val guild_id: Snowflake? = null,
    override val channel_id: Snowflake
) : ChannelEvent