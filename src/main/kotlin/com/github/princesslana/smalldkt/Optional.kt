package com.github.princesslana.smalldkt

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.util.stream.Stream

@Serializable(with = OptionalSerializer::class)
sealed class Optional<T> {
    val isPresent by lazy { this is Some }
    val isAbsent by lazy { this is None }

    data class Some<T>(val value: T) : Optional<T>()
    class None internal constructor() : Optional<Any>() {
        override fun equals(other: Any?): Boolean {
            return other === NONE
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    companion object {
        val NONE: None = None()

        fun <T> absent(): Optional<T> {
            @Suppress("UNCHECKED_CAST")
            return NONE as Optional<T>
        }

        fun <T> some(value: T): Some<T> = Some(value)
    }

    fun ifPresent(block: (T) -> Unit) {
        if (this is Some) block(this.value)
    }

    fun ifAbsent(block: () -> Unit) {
        if (this is None) block()
    }

    fun ifPresentOrElse(block: (T) -> Unit, or: () -> Unit) = if (this is Some) block(this.value) else or()

    fun filter(predicate: (T) -> Boolean): Optional<T> =
        if (this is Some && predicate(this.value)) this
        else absent()

    fun <U> map(mapper: (T) -> U): Optional<U> =
        if (this is Some) some(mapper(this.value))
        else absent()

    fun <U> flatMap(mapper: (T) -> Optional<U>): Optional<U> =
        if (this is Some) mapper(this.value)
        else absent()

    fun or(supplier: () -> Optional<T>): Optional<T> =
        if (isPresent) this
        else supplier()

    fun stream(): Stream<T> =
        if (this is Some) Stream.of(this.value)
        else Stream.empty()

    fun orElse(other: T): T = if (this is Some) this.value else other

    fun orElseGet(otherSuppler: () -> T): T = if (this is Some) this.value else otherSuppler()

    fun orElseThrow(): T = if (this is Some) this.value else throw NoSuchElementException("No value present.")

    fun <X : Exception> orElseThrow(exceptionSupplier: () -> X): T =
        if (this is Some) this.value else throw exceptionSupplier()
}

@Serializer(forClass = Optional::class)
class OptionalSerializer<T>(private val tSerializer: KSerializer<T>) : KSerializer<Optional<T>> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("OptionalSerializer")

    override fun deserialize(decoder: Decoder): Optional<T> {
        return Optional.some(decoder.decodeSerializableValue(tSerializer))
    }

    override fun serialize(encoder: Encoder, obj: Optional<T>) {
        if (obj == Optional.NONE) throw IllegalStateException("Tried to serialize absent optional.")
        encoder.encode(tSerializer, (obj as Optional.Some).value)
    }
}