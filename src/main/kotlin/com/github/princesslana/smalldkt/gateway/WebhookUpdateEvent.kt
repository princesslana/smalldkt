package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.ChannelEvent
import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class WebhookUpdateEvent(
    override val guild_id: Snowflake,
    override val channel_id: Snowflake
) : GuildEvent, ChannelEvent