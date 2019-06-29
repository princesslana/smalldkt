package com.github.princesslana.smalldkt.http

import com.github.princesslana.smalld.Attachment
import com.github.princesslana.smalld.RateLimitException
import com.github.princesslana.smalld.SmallD
import com.github.princesslana.smalldkt.JSON
import kotlinx.coroutines.delay
import kotlinx.serialization.KSerializer
import kotlinx.serialization.internal.UnitSerializer
import kotlin.coroutines.suspendCoroutine

internal suspend fun <V> SmallD.get(path: String, resultSerializer: KSerializer<V>): V {
    return try {
        getThrowing(path, resultSerializer)
    } catch (exception: RateLimitException) {
        delay(exception.expiry.toEpochMilli() - System.currentTimeMillis())
        get(path, resultSerializer)
    }
}

private suspend fun <V> SmallD.getThrowing(path: String, resultSerializer: KSerializer<V>): V =
    suspendCoroutine { continuation ->
        val result = runCatching {
            JSON.parse(resultSerializer, get(path))
        }

        continuation.resumeWith(result)
    }

internal suspend fun <K, V> SmallD.post(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>,
    vararg attachments: Attachment
): V {
    return try {
        postThrowing(payload, payloadSerializer, path, resultSerializer, *attachments)
    } catch (exception: RateLimitException) {
        delay(exception.expiry.toEpochMilli() - System.currentTimeMillis())
        post(payload, payloadSerializer, path, resultSerializer, *attachments)
    }
}

private suspend fun <K, V> SmallD.postThrowing(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>,
    vararg attachments: Attachment
): V = suspendCoroutine { continuation ->
    val result = runCatching {
        JSON.parse(resultSerializer, post(path, compile(payloadSerializer, payload), *attachments))
    }

    continuation.resumeWith(result)
}

internal suspend fun <K, V> SmallD.put(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V {
    return try {
        putThrowing(payload, payloadSerializer, path, resultSerializer)
    } catch (exception: RateLimitException) {
        delay(exception.expiry.toEpochMilli() - System.currentTimeMillis())
        put(payload, payloadSerializer, path, resultSerializer)
    }
}

private suspend fun <K, V> SmallD.putThrowing(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V = suspendCoroutine { continuation ->
    val result = runCatching {
        JSON.parse(resultSerializer, post(path, compile(payloadSerializer, payload)))
    }

    continuation.resumeWith(result)
}

internal suspend fun <K, V> SmallD.patch(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V {
    return try {
        patchThrowing(payload, payloadSerializer, path, resultSerializer)
    } catch (exception: RateLimitException) {
        delay(exception.expiry.toEpochMilli() - System.currentTimeMillis())
        patch(payload, payloadSerializer, path, resultSerializer)
    }
}

private suspend fun <K, V> SmallD.patchThrowing(
    payload: K,
    payloadSerializer: KSerializer<K>,
    path: String,
    resultSerializer: KSerializer<V>
): V = suspendCoroutine { continuation ->
    val result = runCatching {
        JSON.parse(resultSerializer, patch(path, compile(payloadSerializer, payload)))
    }

    continuation.resumeWith(result)
}

internal suspend fun <V> SmallD.delete(path: String, resultSerializer: KSerializer<V>): V {
    return try {
        deleteThrowing(path, resultSerializer)
    } catch (exception: RateLimitException) {
        delay(exception.expiry.toEpochMilli() - System.currentTimeMillis())
        delete(path, resultSerializer)
    }
}

private suspend fun <V> SmallD.deleteThrowing(path: String, resultSerializer: KSerializer<V>): V =
    suspendCoroutine { continuation ->
        val result = kotlin.runCatching {
            JSON.parse(resultSerializer, delete(path))
        }

        continuation.resumeWith(result)
    }

private fun <T> compile(payloadSerializer: KSerializer<T>, payload: T): String = when (payloadSerializer) {
    UnitSerializer -> "{}"
    else -> JSON.stringify(payloadSerializer, payload)
}
