package com.github.princesslana.smalldkt.type

interface Identifiable {
    val id: Snowflake
}

interface ChannelContainer {
    val channel_id: Snowflake
}