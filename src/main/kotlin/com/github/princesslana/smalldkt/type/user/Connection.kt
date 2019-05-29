package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.type.guild.Integration
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.lang.IllegalStateException

@Serializable
data class Connection(
    val id: String,
    val name: String,
    val type: String,
    val revoked: Boolean,
    val integrations: List<Integration>,
    val verified: Boolean,
    @SerialName("friend_sync") val friendSync: Boolean,
    @SerialName("show_activity") val showActivity: Boolean,
    val visibility: VisibilityType
)

@Serializable
enum class VisibilityType(val value: Int) {
    NONE(0),
    EVERYONE(1);

    @Serializer(forClass = VisibilityType::class)
    companion object : KSerializer<VisibilityType> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("VisibilityTypeSerializer")

        override fun deserialize(decoder: Decoder): VisibilityType {
            return when (decoder.decodeInt()) {
                0 -> NONE
                1 -> EVERYONE
                else -> throw IllegalStateException("Illegal Visibility Type.")
            }
        }

        override fun serialize(encoder: Encoder, obj: VisibilityType) {
            encoder.encodeInt(obj.value)
        }

    }
}