package com.github.princesslana.smalldkt

import com.github.princesslana.smalldkt.type.channel.Channel

interface Event

interface ChannelEvent : Event {
    val eventChannel: Channel
}

interface GuildEvent : Event