package com.github.princesslana.smalldkt.type.voice

import kotlinx.serialization.Serializable

@Serializable
data class VoiceRegion(
    val id: String,
    val name: String,
    val vip: Boolean,
    val optimal: Boolean,
    val deprecated: Boolean,
    val custom: Boolean
)