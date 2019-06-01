package com.github.princesslana.smalldkt.type

interface Event

interface ChannelEvent : Event, ChannelContainer

interface GuildEvent : Event {
    val guild_id: Snowflake
}