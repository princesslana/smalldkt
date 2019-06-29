package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    val id: Snowflake,
    val filename: String,
    val size: Int,
    val url: String,
    val proxy_url: String,
    val height: Int?,
    val width: Int?
)