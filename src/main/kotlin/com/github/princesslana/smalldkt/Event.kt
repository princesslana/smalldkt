package com.github.princesslana.smalldkt

import com.github.princesslana.smalldkt.type.Snowflake

interface Event

interface ChannelEvent : Event {
    val channel_id: Snowflake
}

interface MessageEvent : ChannelEvent {
    val message_id: Snowflake
}

interface GuildEvent : Event {
    val guild_id: Snowflake
}