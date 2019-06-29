package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.emoji.Emoji
import kotlinx.serialization.Serializable

@Serializable
data class GuildEmojisUpdateEvent(
    override val guild_id: Snowflake,
    val emojis: List<Emoji>
) : GuildEvent