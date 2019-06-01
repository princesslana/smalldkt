package com.github.princesslana.smalldkt.type.channel.resources

import com.github.princesslana.smalldkt.type.emoji.resources.Emoji
import kotlinx.serialization.Serializable

@Serializable(with = ReactionImpl.`$serializer`::class)
interface Reaction {
    val count: Int
    val me: Boolean
    val emoji: Emoji
}

@Serializable
data class ReactionImpl(override val count: Int, override val me: Boolean, override val emoji: Emoji) : Reaction