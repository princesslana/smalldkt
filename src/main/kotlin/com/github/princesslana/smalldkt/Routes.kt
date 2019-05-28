package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.Attachment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ImplicitReflectionSerializer
internal suspend inline fun <reified V : Any> get(smallD: SmallDData, path: String): V =
    suspendCoroutine { continuation ->
        continuation.resume(JSON.parse(smallD.smallD.get(path)))
    }

@ImplicitReflectionSerializer
internal suspend inline fun <reified K : Any, reified V : Any> post(
    smallD: SmallDData,
    payload: K,
    path: String,
    vararg attachments: Attachment
): V = suspendCoroutine { continuation ->
    continuation.resume(JSON.parse(smallD.smallD.post(path, JSON.stringify(payload), *attachments)))
}

@ImplicitReflectionSerializer
internal suspend inline fun <reified K : Any, reified V : Any> put(
    smallD: SmallDData,
    payload: K,
    path: String
): V = suspendCoroutine { continuation ->
    continuation.resume(JSON.parse(smallD.smallD.put(path, JSON.stringify(payload))))
}

@ImplicitReflectionSerializer
internal suspend inline fun <reified K : Any, reified V : Any> patch(
    smallD: SmallDData,
    payload: K,
    path: String
): V = suspendCoroutine { continuation ->
    continuation.resume(JSON.parse(smallD.smallD.patch(path, JSON.stringify(payload))))
}

internal fun delete(smallD: SmallDData, path: String) {
    GlobalScope.launch { smallD.smallD.delete(path) }
}