package com.github.princesslana.smalldkt.type.message

import com.github.princesslana.smalldkt.type.emoji.Emoji
import kotlinx.serialization.Serializable

@Serializable
data class Reaction(
    val count: Int,
    val me: Boolean,
    val emoji: Emoji
)