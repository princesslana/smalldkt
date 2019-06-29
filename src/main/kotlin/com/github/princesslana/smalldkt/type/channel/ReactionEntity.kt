package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.type.emoji.PartialEmoji
import kotlinx.serialization.Serializable

@Serializable
data class Reaction(
    val count: Int,
    val me: Boolean,
    val emoji: PartialEmoji
)