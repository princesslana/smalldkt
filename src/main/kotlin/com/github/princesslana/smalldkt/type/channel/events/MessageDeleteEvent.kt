package com.github.princesslana.smalldkt.type.channel.events

import com.github.princesslana.smalldkt.type.ChannelEvent
import com.github.princesslana.smalldkt.type.Identifiable
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

// Message Delete
// https://discordapp.com/developers/docs/topics/gateway#message-delete

@Serializable
data class MessageDeleteEvent(
    val guild_id: Snowflake? = null,
    override val id: Snowflake,
    override val channel_id: Snowflake
) : Identifiable, ChannelEvent