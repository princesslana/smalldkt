package com.github.princesslana.smalldkt

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable(with = WrapperSerializer::class)
open class Wrapper<T>(val value: T)

@Serializer(forClass = Wrapper::class)
class WrapperSerializer<T>(private val tSerializer: KSerializer<T>) : KSerializer<Wrapper<T>> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("WrapperSerializer")

    override fun deserialize(decoder: Decoder): Wrapper<T> {
        return Wrapper(decoder.decodeSerializableValue(tSerializer))
    }

    override fun serialize(encoder: Encoder, obj: Wrapper<T>) {
        encoder.encode(tSerializer, obj.value)
    }
}