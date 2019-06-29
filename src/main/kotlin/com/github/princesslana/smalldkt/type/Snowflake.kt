package com.github.princesslana.smalldkt.type

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class Snowflake(val id: Long) {
    override fun toString(): String = "$id"

    @Serializer(forClass = Snowflake::class)
    companion object : KSerializer<Snowflake> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("SnowflakeSerializer")

        override fun deserialize(decoder: Decoder): Snowflake {
            return Snowflake(decoder.decodeLong())
        }

        override fun serialize(encoder: Encoder, obj: Snowflake) {
            encoder.encodeLong(obj.id)
        }
    }
}