package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.type.guild.PartialIntegration
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class Connection(
    val id: String,
    val name: String,
    val type: String,
    val revoked: Boolean,
    val integrations: List<PartialIntegration>,
    val verified: Boolean,
    val friend_sync: Boolean,
    val show_activity: Boolean,
    val visibility: Int
)

@Serializable(VisibilityTypeSerializer::class)
enum class VisibilityType(val value: Int) {
    NONE(0),
    EVERYONE(1)
}

@Serializer(VisibilityType::class)
class VisibilityTypeSerializer : KSerializer<VisibilityType> {
    override val descriptor = StringDescriptor.withName("VisbilityTypeSerializer")

    override fun deserialize(decoder: Decoder): VisibilityType {
        return when (decoder.decodeInt()) {
            0 -> VisibilityType.NONE
            1 -> VisibilityType.EVERYONE
            else -> throw IllegalStateException("Illegal value passed for visbility type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: VisibilityType) {
        encoder.encodeInt(obj.value)
    }
}