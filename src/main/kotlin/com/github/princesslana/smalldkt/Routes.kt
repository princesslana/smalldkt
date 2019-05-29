package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.Attachment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal suspend fun <V> get(smallD: SmallDData, path: String, resultSerializer: KSerializer<V>): V =
    suspendCoroutine { continuation ->
        continuation.resume(JSON.parse(resultSerializer, smallD.smallD.get(path)))
    }

internal suspend fun <K, V> post(
    smallD: SmallDData,
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>,
    vararg attachments: Attachment
): V = suspendCoroutine { continuation ->
    continuation.resume(
        JSON.parse(
            resultSerializer,
            smallD.smallD.post(path, JSON.stringify(payloadSerializer, payload), *attachments)
        )
    )
}

internal suspend inline fun <K, V> put(
    smallD: SmallDData,
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V = suspendCoroutine { continuation ->
    continuation.resume(
        JSON.parse(
            resultSerializer,
            smallD.smallD.put(path, JSON.stringify(payloadSerializer, payload))
        )
    )
}

internal suspend inline fun <K, V> patch(
    smallD: SmallDData,
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V = suspendCoroutine { continuation ->
    continuation.resume(
        JSON.parse(
            resultSerializer,
            smallD.smallD.patch(path, JSON.stringify(payloadSerializer, payload))
        )
    )
}

internal fun delete(smallD: SmallDData, path: String) {
    GlobalScope.launch { smallD.smallD.delete(path) }
}