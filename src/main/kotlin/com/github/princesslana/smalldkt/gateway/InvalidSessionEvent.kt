package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.Event
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable(InvalidSessionEventSerializer::class)
data class InvalidSessionEvent(
    val resumable: Boolean
) : Event

@Serializer(InvalidSessionEvent::class)
class InvalidSessionEventSerializer : KSerializer<InvalidSessionEvent> {
    override val descriptor = StringDescriptor.withName("InvalidSessionEventSerializer")

    override fun deserialize(decoder: Decoder): InvalidSessionEvent {
        return InvalidSessionEvent(decoder.decodeBoolean())
    }

    override fun serialize(encoder: Encoder, obj: InvalidSessionEvent) {
        encoder.encodeBoolean(obj.resumable)
    }
}