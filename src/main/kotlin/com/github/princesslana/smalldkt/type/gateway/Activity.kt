package com.github.princesslana.smalldkt.type.gateway

import com.github.princesslana.smalldkt.Optional
import com.github.princesslana.smalldkt.Snowflake
import kotlinx.serialization.*
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class Activity(
    val name: String,
    val type: ActivityType,
    val url: Optional<String>? = Optional.absent(),
    val timestamps: ActivityTimestamps? = null,
    @SerialName("application_id") val applicationId: Snowflake? = null,
    val details: Optional<String>? = Optional.absent(),
    val state: Optional<String>? = Optional.absent(),
    val party: ActivityParty? = null,
    val assets: ActivityAssets? = null,
    val secrets: ActivitySecrets? = null,
    val instance: Boolean? = null,
    val flags: Int? = null
)

@Serializable
enum class ActivityType(val value: Int) {
    GAME(0),
    STREAMING(1),
    LISTENING(2);

    @Serializer(forClass = ActivityType::class)
    companion object : KSerializer<ActivityType> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("ActivityTypeSerializer")

        override fun deserialize(decoder: Decoder): ActivityType {
            return when (decoder.decodeInt()) {
                0 -> GAME
                1 -> STREAMING
                2 -> LISTENING
                else -> throw IllegalStateException("Illegal Activity Type.")
            }
        }

        override fun serialize(encoder: Encoder, obj: ActivityType) {
            encoder.encodeInt(obj.value)
        }
    }
}

@Serializable
data class ActivityTimestamps(val start: Int? = null, val end: Int? = null)

@Serializable
data class ActivityParty(val id: String? = null, val size: ActivityPartySize)

@Serializable
data class ActivityPartySize(val currentSize: Int, val maxSize: Int) {
    @Serializer(forClass = ActivityPartySize::class)
    companion object : KSerializer<ActivityPartySize> {
        val serializer = ArrayListSerializer(Int.serializer())
        override val descriptor: SerialDescriptor = StringDescriptor.withName("ActivityPartySizeSerializer")

        override fun deserialize(decoder: Decoder): ActivityPartySize {
            val list = decoder.decode(serializer)
            return ActivityPartySize(list[0], list[1])
        }

        override fun serialize(encoder: Encoder, obj: ActivityPartySize) {
            encoder.encode(serializer, listOf(obj.currentSize, obj.maxSize))
        }
    }
}

@Serializable
data class ActivityAssets(
    @SerialName("large_image") val largeImage: String? = null,
    @SerialName("large_text") val largeText: String? = null,
    @SerialName("small_image") val smallImage: String? = null,
    @SerialName("small_text") val smallText: String? = null
)

@Serializable
data class ActivitySecrets(val join: String? = null, val spectate: String? = null, val match: String? = null)

enum class ActivityFlag(private val shift: Int, val value: Int = 1 shl shift) {
    INSTANCE(0),
    JOIN(1),
    SPECTATE(2),
    JOIN_REQUEST(3),
    SYNC(4),
    PLAY(5);

    companion object {
        fun compile(vararg flags: ActivityFlag): Int {
            return flags.fold(0, { x, f -> x or f.value })
        }
    }
}