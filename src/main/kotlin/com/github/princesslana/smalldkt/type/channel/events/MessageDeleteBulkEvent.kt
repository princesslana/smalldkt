package com.github.princesslana.smalldkt.type.channel.events

import com.github.princesslana.smalldkt.type.ChannelEvent
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

// Message Delete Bulk
// https://discordapp.com/developers/docs/topics/gateway#message-delete-bulk

@Serializable
data class MessageDeleteBulkEvent(
    val ids: List<Snowflake>,
    val guild_id: Snowflake? = null,
    override val channel_id: Snowflake
) : ChannelEvent