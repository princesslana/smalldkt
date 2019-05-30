package com.github.princesslana.smalldkt.type.message

import com.github.princesslana.smalldkt.TimeStamp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Embed(
    val title: String? = null,
    val type: String? = null,
    val description: String? = null,
    val url: String? = null,
    val timeStamp: TimeStamp? = null,
    val color: Int? = null,
    val footer: EmbedFooter? = null,
    val image: EmbedImage? = null,
    val thumbnail: EmbedThumbnail? = null,
    val video: EmbedVideo? = null,
    val provider: EmbedProvider? = null,
    val author: EmbedAuthor? = null,
    val fields: List<EmbedField>? = null
)

@Serializable
data class EmbedThumbnail(
    val url: String? = null,
    @SerialName("proxy_url") val proxyUrl: String? = null,
    val height: Int? = null,
    val width: Int? = null
)

@Serializable
data class EmbedVideo(
    val url: String? = null,
    val height: Int? = null,
    val width: Int? = null
)

@Serializable
data class EmbedImage(
    val url: String? = null,
    @SerialName("proxy_url") val proxyUrl: String? = null,
    val height: Int? = null,
    val width: Int? = null
)

@Serializable
data class EmbedProvider(
    val name: String? = null,
    val url: String? = null
)

@Serializable
data class EmbedAuthor(
    val name: String? = null,
    val url: String? = null,
    @SerialName("icon_url") val iconUrl: String? = null,
    @SerialName("proxy_icon_url") val proxyIconUrl: String? = null
)

@Serializable
data class EmbedFooter(
    val text: String,
    @SerialName("icon_url") val iconUrl: String? = null,
    @SerialName("proxy_icon_url") val proxyIconUrl: String? = null
)

@Serializable
data class EmbedField(
    val name: String,
    val value: String,
    val inline: Boolean? = null
)