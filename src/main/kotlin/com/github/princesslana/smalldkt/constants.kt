package com.github.princesslana.smalldkt

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Suppress("EXPERIMENTAL_API_USAGE")
val JSON = Json(JsonConfiguration(encodeDefaults = false))
const val DEFAULT_CHANNEL_BUFFER = 25