package com.github.princesslana.smalldkt.dsl

import com.github.princesslana.smalldkt.type.Builder
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.channel.*

@DslMarker
annotation class EmbedDsl

@EmbedDsl
class EmbedBuilder(
    var title: String? = null,
    var type: String? = null,
    var description: String? = null,
    var url: String? = null,
    var timeStamp: TimeStamp? = null,
    var color: Int? = null,
    var footer: EmbedFooter? = null,
    var image: EmbedImage? = null,
    var thumbnail: EmbedThumbnail? = null,
    var video: EmbedVideo? = null,
    var provider: EmbedProvider? = null,
    var author: EmbedAuthor? = null,
    private val fields: MutableList<EmbedField> = mutableListOf()
) : Builder<Embed> {
    fun field(title: String, inline: Boolean? = null, lazyValue: () -> String) {
        if (fields.size == EmbedLimits.FIELDS) {
            throw IllegalStateException("Cannot add more fields to this embed: reached limit")
        }
        field(title, inline, lazyValue())
    }

    fun field(title: String, inline: Boolean? = null, value: String) {
        if (fields.size == EmbedLimits.FIELDS) {
            throw IllegalStateException("Cannot add more fields to this embed: reached limit")
        }
        fields.add(EmbedFieldBuilder(title, value, inline).build())
    }

    fun addTimestampNow() {
        timeStamp = TimeStamp(System.currentTimeMillis())
    }

    fun footer(text: String, block: EmbedFooterBuilder.() -> Unit) {
        footer = EmbedFooterBuilder(text).also(block).build()
    }

    fun image(block: EmbedImageBuilder.() -> Unit) {
        image = EmbedImageBuilder().also(block).build()
    }

    fun thumbnail(block: EmbedThumbnailBuilder.() -> Unit) {
        thumbnail = EmbedThumbnailBuilder().also(block).build()
    }

    fun provider(block: EmbedProviderBuilder.() -> Unit) {
        provider = EmbedProviderBuilder().also(block).build()
    }

    fun author(block: EmbedAuthorBuilder.() -> Unit) {
        author = EmbedAuthorBuilder().also(block).build()
    }

    fun colorFromHex(hex: String) {
        color = hex.toInt(16)
    }

    fun colourFromHex(hex: String) = colorFromHex(hex) // okay fox

    override fun build(): Embed =
        Embed(
            title,
            type,
            description,
            url,
            timeStamp,
            color,
            footer,
            image,
            thumbnail,
            video,
            provider,
            author,
            if (fields.isEmpty()) null else fields
        )
}

@EmbedDsl
data class EmbedThumbnailBuilder(
    var url: String? = null,
    var proxyUrl: String? = null,
    var height: Int? = null,
    var width: Int? = null
) : Builder<EmbedThumbnail> {
    override fun build(): EmbedThumbnail =
        EmbedThumbnail(url, proxyUrl, height, width)
}

@EmbedDsl
data class EmbedVideoBuilder(
    var url: String? = null,
    var height: Int? = null,
    var width: Int? = null
) : Builder<EmbedVideo> {
    override fun build(): EmbedVideo =
        EmbedVideo(url, height, width)
}

@EmbedDsl
data class EmbedImageBuilder(
    var url: String? = null,
    var proxyUrl: String? = null,
    var height: Int? = null,
    var width: Int? = null
) : Builder<EmbedImage> {
    override fun build(): EmbedImage =
        EmbedImage(url, proxyUrl, height, width)
}

@EmbedDsl
data class EmbedProviderBuilder(
    var name: String? = null,
    var url: String? = null
) : Builder<EmbedProvider> {
    override fun build(): EmbedProvider =
        EmbedProvider(name, url)
}

@EmbedDsl
data class EmbedAuthorBuilder(
    var name: String? = null,
    var url: String? = null,
    var iconUrl: String? = null,
    var proxyIconUrl: String? = null
) : Builder<EmbedAuthor> {
    override fun build(): EmbedAuthor =
        EmbedAuthor(name, url, iconUrl, proxyIconUrl)
}

@EmbedDsl
data class EmbedFooterBuilder(
    var text: String,
    var iconUrl: String? = null,
    var proxyIconUrl: String? = null
) : Builder<EmbedFooter> {
    override fun build(): EmbedFooter =
        EmbedFooter(text, iconUrl, proxyIconUrl)
}

@EmbedDsl
class EmbedFieldBuilder(
    var name: String?,
    var value: String?,
    var inline: Boolean? = null
) : Builder<EmbedField> {
    override fun build(): EmbedField {
        require(name != null)
        require(value != null)
        return EmbedField(name!!, value!!, inline)
    }
}

fun embed(block: EmbedBuilder.() -> Unit) = EmbedBuilder().also(block).build()