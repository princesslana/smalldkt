package com.github.princesslana.smalldkt.type.gateway.resources

import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.user.user.PartialUserImpl
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor


interface ActivityTimeStamps{
    val start: Int?
    val end: Int?
}

@Serializable
data class ActivityTimeStampsImpl(
        override val start: Int? = null,
        override val end: Int? = null
): ActivityTimeStamps

interface ActivityParty{
    val id: String?
    val size: List<Int>?
}
@Serializable
data class ActivityPartyImpl(
        override val id: String? = null,
        override val size: List<Int>? = null
): ActivityParty

interface ActivityAssets{
    val large_image: String?
    val large_text: String?
    val small_image: String?
    val small_text: String?
}

@Serializable
data class ActivityAssetsImpl(
        override val large_image: String? = null,
        override val large_text: String? = null,
        override val small_image: String? = null,
        override val small_text: String? = null
): ActivityAssets

interface ActivitySecrets{
    val join: String?
    val spectate: String?
    val match: String?
}

@Serializable
data class ActivitySecretsImpl(
        override val join: String? = null,
        override val spectate: String? = null,
        override val match: String? = null
): ActivitySecrets

interface Activity{
    val name: String
    val type: ActivityType
    val url: Optional<String>?
    val timestamps: ActivityTimeStampsImpl?
    val application_id: Snowflake?
    val details: Optional<String>?
    val state: Optional<String>?
    val party: ActivityPartyImpl?
    val assets: ActivityAssetsImpl?
    val secrets: ActivitySecretsImpl?
    val instance: Boolean?
    val flags: Int?
}

@Serializable
data class ActivityImpl(
        override val name: String,
        override val type: ActivityType,
        override val url: Optional<String>? = Optional.absent(),
        override val timestamps: ActivityTimeStampsImpl? = null,
        override val application_id: Snowflake? = null,
        override val details: Optional<String>? = Optional.absent(),
        override val state: Optional<String>? = Optional.absent(),
        override val party: ActivityPartyImpl? = null,
        override val assets: ActivityAssetsImpl? = null,
        override val secrets: ActivitySecretsImpl? = null,
        override val instance: Boolean? = null,
        override val flags: Int? = null
): Activity

interface ClientStatus{
    val desktop: String?
    val mobile: String?
    val web: String?
}

@Serializable
data class ClientStatusImpl(
        override val desktop: String? = null,
        override val mobile: String? = null,
        override val web: String? = null
): ClientStatus

interface PartialPresenceUpdate{
    val user: PartialUserImpl?
    val roles: List<Snowflake>?
    val game: Optional<ActivityImpl>?
    val guild_id: Snowflake?
    val status: String?
    val activities: List<ActivityImpl>?
    val client_status: ClientStatusImpl?
}

@Serializable
data class PartialPresenceUpdateImpl(
        override val user: PartialUserImpl? = null,
        override val roles: List<Snowflake>? = null,
        override val game: Optional<ActivityImpl>? = Optional.absent(),
        override val guild_id: Snowflake? = null,
        override val status: String? = null,
        override val activities: List<ActivityImpl>? = null,
        override val client_status: ClientStatusImpl? = null
): PartialPresenceUpdate

@Serializable(with = ActivityTypeSerializer::class)
enum class ActivityType(val type: Int){
    GAME(0),
    STREAMING(1),
    LISTENING(2)
}


@Serializer(forClass = ActivityType::class)
class ActivityTypeSerializer : KSerializer<ActivityType> {

    override val descriptor: SerialDescriptor = StringDescriptor.withName("ActivityTypeSerializer")

    override fun deserialize(decoder: Decoder): ActivityType {
        return when (decoder.decodeInt()) {
            0 -> ActivityType.GAME
            1 -> ActivityType.STREAMING
            2 -> ActivityType.LISTENING
            else -> throw IllegalStateException("Illegal Activity Type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: ActivityType) {
        encoder.encodeInt(obj.type)
    }
}
