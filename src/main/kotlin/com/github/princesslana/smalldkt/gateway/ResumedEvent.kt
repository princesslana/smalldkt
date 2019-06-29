package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.Event
import kotlinx.serialization.Serializable

@Serializable
data class ResumedEvent(
    val _trace: List<String>
) : Event