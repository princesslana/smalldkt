package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.Event
import kotlinx.serialization.Serializable

@Serializable
data class HelloEvent(
    val heartbeat_interval: Int,
    val _trace: List<String>
) : Event