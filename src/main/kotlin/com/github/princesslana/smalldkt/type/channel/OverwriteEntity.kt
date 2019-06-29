package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class Overwrite(
    val id: Snowflake,
    val type: OverwriteType,
    val allow: Int,
    val deny: Int
)

@Serializable(with = OverwriteTypeSerializer::class)
enum class OverwriteType(val value: String) {
    ROLE("role"),
    MEMBER("member")
}

@Serializer(forClass = OverwriteType::class)
class OverwriteTypeSerializer : KSerializer<OverwriteType> {
    override val descriptor = StringDescriptor.withName("OverwriteTypeSerializer")

    override fun deserialize(decoder: Decoder): OverwriteType {
        return when (decoder.decodeString()) {
            "role" -> OverwriteType.ROLE
            "member" -> OverwriteType.MEMBER
            else -> throw IllegalStateException("Illegal value passed for overwrite type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: OverwriteType) {
        encoder.encodeString(obj.value)
    }
}