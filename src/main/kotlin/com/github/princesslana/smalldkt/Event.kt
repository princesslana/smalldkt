package com.github.princesslana.smalldkt

interface Event

interface ChannelEvent : Event {
    val eventChannel: Snowflake
}

interface GuildEvent : Event