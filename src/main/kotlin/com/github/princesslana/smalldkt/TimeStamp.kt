package com.github.princesslana.smalldkt

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Serializable(with = TimeStampSerializer::class)
data class TimeStamp(val time: LocalDateTime) {
    constructor(string: String) : this(LocalDateTime.parse(string, DateTimeFormatter.ISO_DATE_TIME))
    constructor(millis: Long) : this(LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()))

    override fun toString(): String {
        return time.toString()
    }
}

@Serializer(forClass = TimeStamp::class)
class TimeStampSerializer : KSerializer<TimeStamp> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("TimeStampSerializer")

    override fun serialize(encoder: Encoder, obj: TimeStamp) {
        encoder.encodeString(obj.toString())
    }

    override fun deserialize(decoder: Decoder): TimeStamp {
        return TimeStamp(decoder.decodeString())
    }
}