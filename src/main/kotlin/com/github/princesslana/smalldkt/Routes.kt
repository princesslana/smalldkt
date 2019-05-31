package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.Attachment
import com.github.princesslana.smalld.SmallD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.internal.UnitSerializer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val ioCoroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

internal suspend fun <V> SmallD.get(path: String, resultSerializer: KSerializer<V>): V =
    suspendCoroutine { continuation ->
        continuation.resume(JSON.parse(resultSerializer, get(path)))
    }


internal suspend fun <K, V> SmallD.post(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>,
    vararg attachments: Attachment
): V = when (resultSerializer) {
    UnitSerializer -> {
        ioCoroutineScope.launch { post(path, compile(payloadSerializer, payload), *attachments) }
        @Suppress("UNCHECKED_CAST") // we already checked due to the when statement limiting it.
        Unit as V
    }
    else -> suspendCoroutine { continuation ->
        continuation.resume(
            JSON.parse(
                resultSerializer,
                post(path, compile(payloadSerializer, payload), *attachments)
            )
        )
    }
}

internal suspend inline fun <K, V> SmallD.put(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V = when (resultSerializer) {
    UnitSerializer -> {
        ioCoroutineScope.launch { put(path, compile(payloadSerializer, payload)) }
        @Suppress("UNCHECKED_CAST") // we already checked due to the when statement limiting it.
        Unit as V
    }
    else -> suspendCoroutine { continuation ->
        continuation.resume(
            JSON.parse(
                resultSerializer,
                put(path, compile(payloadSerializer, payload))
            )
        )
    }
}

internal suspend inline fun <K, V> SmallD.patch(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V = when (resultSerializer) {
    UnitSerializer -> {
        ioCoroutineScope.launch { patch(path, compile(payloadSerializer, payload)) }
        @Suppress("UNCHECKED_CAST") // we already checked due to the when statement limiting it.
        Unit as V
    }
    else -> suspendCoroutine { continuation ->
        continuation.resume(
            JSON.parse(
                resultSerializer,
                patch(path, compile(payloadSerializer, payload))
            )
        )
    }
}

internal suspend fun <V> SmallD.delete(path: String, resultSerializer: KSerializer<V>): V = when (resultSerializer) {
    UnitSerializer -> {
        ioCoroutineScope.launch { delete(path) }
        @Suppress("UNCHECKED_CAST") // we already checked due to the when statement limiting it.
        Unit as V
    }
    else -> suspendCoroutine { continuation ->
        continuation.resume(
            JSON.parse(
                resultSerializer,
                delete(path)
            )
        )
    }
}

private fun <T> compile(payloadSerializer: KSerializer<T>, payload: T): String = when (payloadSerializer) {
    UnitSerializer -> "{}"
    else -> JSON.stringify(payloadSerializer, payload)
}