package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.Attachment
import com.github.princesslana.smalld.SmallD
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
): V = suspendCoroutine { continuation ->
    continuation.resume(
        JSON.parse(
            resultSerializer,
            post(path, JSON.stringify(payloadSerializer, payload), *attachments)
        )
    )
}

internal suspend inline fun <K, V> SmallD.put(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V = suspendCoroutine { continuation ->
    continuation.resume(
        JSON.parse(
            resultSerializer,
            put(path, JSON.stringify(payloadSerializer, payload))
        )
    )
}

internal suspend inline fun <K, V> SmallD.patch(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V = suspendCoroutine { continuation ->
    continuation.resume(
        JSON.parse(
            resultSerializer,
            patch(path, JSON.stringify(payloadSerializer, payload))
        )
    )
}
// TODO
internal fun SmallD.delete(path: String) {
    GlobalScope.launch { delete(path) }
}