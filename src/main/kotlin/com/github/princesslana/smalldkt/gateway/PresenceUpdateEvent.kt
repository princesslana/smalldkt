package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.Event
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.user.PartialUser
import kotlinx.serialization.*
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class PresenceUpdateEvent(
    val user: PartialUser? = null,
    val roles: List<Snowflake>? = null,
    val game: Optional<Activity>? = Optional.absent(),
    val guild_id: Snowflake? = null,
    val status: String? = null,
    val activities: List<Activity>? = null,
    val client_status: ClientStatus? = null
) : Event

@Serializable
data class ClientStatus(
    val desktop: String? = null,
    val mobile: String? = null,
    val web: String? = null
)

@Serializable
data class Activity(
    val name: String,
    val type: ActivityType,
    val url: Optional<String>? = Optional.absent(),
    val timestamps: ActivityTimestamps? = null,
    val application_id: Snowflake? = null,
    val details: Optional<String>? = Optional.absent(),
    val state: Optional<String>? = Optional.absent(),
    val party: ActivityParty? = null,
    val assets: ActivityAssets? = null,
    val secrets: ActivitySecrets? = null,
    val instance: Boolean? = null,
    val flags: Int? = null
)

@Serializable(ActivityTypeSerializer::class)
enum class ActivityType(val value: Int) {
    GAME(0),
    STREAMING(1),
    LISTENING(2)
}

@Serializer(ActivityType::class)
class ActivityTypeSerializer : KSerializer<ActivityType> {
    override val descriptor = StringDescriptor.withName("ActivityTypeSerializer")

    override fun deserialize(decoder: Decoder): ActivityType {
        return when (decoder.decodeInt()) {
            0 -> ActivityType.GAME
            1 -> ActivityType.STREAMING
            2 -> ActivityType.LISTENING
            else -> throw IllegalStateException("Illegal value passed for activity type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: ActivityType) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable
data class ActivityTimestamps(
    val start: Int? = null,
    val end: Int? = null
)

@Serializable
data class ActivityParty(
    val id: String? = null,
    val size: ActivityPartySize? = null
)

@Serializable(ActivityPartySizeSerializer::class)
data class ActivityPartySize(val currentSize: Int, val maxSize: Int)

@Serializer(ActivityPartySize::class)
class ActivityPartySizeSerializer : KSerializer<ActivityPartySize> {
    override val descriptor = StringDescriptor.withName("ActivityPartySizeSerializer")

    override fun deserialize(decoder: Decoder): ActivityPartySize {
        val list = decoder.decode(ArrayListSerializer(Int.serializer()))
        return ActivityPartySize(list[0], list[1])
    }

    override fun serialize(encoder: Encoder, obj: ActivityPartySize) {
        encoder.encode(ArrayListSerializer(Int.serializer()), listOf(obj.currentSize, obj.maxSize))
    }
}

@Serializable
data class ActivityAssets(
    val large_image: String? = null,
    val large_text: String? = null,
    val small_image: String? = null,
    val small_text: String? = null
)

@Serializable
data class ActivitySecrets(
    val join: String? = null,
    val spectate: String? = null,
    val match: String? = null
)

enum class ActivityFlag(shift: Int, private val value: Long = 1L shl shift) {
    INSTANCE(0),
    JOIN(1),
    SPECTATE(2),
    JOIN_REQUEST(3),
    SYNC(4),
    PLAY(5);

    fun hasFlag(flags: Long, flag: ActivityFlag): Boolean {
        return (flags and flag.value) == flag.value
    }

    fun compileFlags(vararg flags: ActivityFlag): Long {
        return flags.fold(0L, { a, f -> a or f.value })
    }
}