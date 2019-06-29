package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.type.TimeStamp
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
) {
    init {
        require(title?.length ?: 0 <= EmbedLimits.TITLE_LEN)
        require(description?.length ?: 0 <= EmbedLimits.DESCRIPTION_LEN)
        require(fields?.size?.let { it <= EmbedLimits.FIELDS } ?: true)
    }
}

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
) {
    init {
        require(name?.length ?: 0 <= EmbedLimits.AUTHOR_NAME_LEN)
    }
}

@Serializable
data class EmbedFooter(
    val text: String,
    @SerialName("icon_url") val iconUrl: String? = null,
    @SerialName("proxy_icon_url") val proxyIconUrl: String? = null
) {
    init {
        require(text.length <= EmbedLimits.FOOTER_TEXT_LEN)
    }
}

@Serializable
data class EmbedField(
    val name: String,
    val value: String,
    val inline: Boolean? = null
) {
    init {
        require(name.length <= EmbedLimits.FIELD_NAME_LEN)
        require(value.length <= EmbedLimits.FIELD_VALUE_LEN)
    }
}

object EmbedLimits {
    const val TITLE_LEN: Int = 256
    const val DESCRIPTION_LEN: Int = 2048
    const val FIELDS: Int = 25
    const val FIELD_NAME_LEN: Int = 256
    const val FIELD_VALUE_LEN: Int = 1024
    const val FOOTER_TEXT_LEN: Int = 2048
    const val AUTHOR_NAME_LEN: Int = 256
}