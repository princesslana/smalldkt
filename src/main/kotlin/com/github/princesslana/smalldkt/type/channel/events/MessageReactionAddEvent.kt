package com.github.princesslana.smalldkt.type.channel.events

import com.github.princesslana.smalldkt.type.ChannelEvent
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.emoji.resources.Emoji
import kotlinx.serialization.Serializable

// Message Reaction Add
// https://discordapp.com/developers/docs/topics/gateway#message-reaction-add

@Serializable
data class MessageReactionAddEvent(
    val user_id: Snowflake,
    val message_id: Snowflake,
    val guild_id: Snowflake? = null,
    val emoji: Emoji,
    override val channel_id: Snowflake
): ChannelEvent