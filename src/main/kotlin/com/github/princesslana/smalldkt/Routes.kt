package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.Attachment
import com.github.princesslana.smalld.SmallD
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

@ImplicitReflectionSerializer
internal inline fun <reified V : Any> get(smallD: SmallDData, path: String, crossinline acceptor: (V) -> Unit) {
    val deferred = GlobalScope.async { smallD.smallD.get(path) }
    deferred.invokeOnCompletion {
        if (it == null) {
            // TODO LOGGING
        } else {
            acceptor(JSON.parse(deferred.getCompleted()))
        }
    }
}

@ImplicitReflectionSerializer
internal inline fun <reified K : Any, reified V : Any> post(
    smallD: SmallDData,
    payload: K,
    path: String,
    vararg attachments: Attachment,
    crossinline acceptor: (V) -> Unit
) {
    val deferred = GlobalScope.async { smallD.smallD.post(path, JSON.stringify(payload), *attachments) }
    deferred.invokeOnCompletion {
        if (it == null) {
            // TODO LOGGING
        } else {
            acceptor(JSON.parse(deferred.getCompleted()))
        }
    }
}

@ImplicitReflectionSerializer
internal inline fun <reified K : Any, reified V : Any> put(
    smallD: SmallDData,
    payload: K,
    path: String,
    crossinline acceptor: (V) -> Unit
) {
    val deferred = GlobalScope.async { smallD.smallD.put(path, JSON.stringify(payload)) }
    deferred.invokeOnCompletion {
        if (it == null) {
            // TODO LOGGING
        } else {
            acceptor(JSON.parse(deferred.getCompleted()))
        }
    }
}

@ImplicitReflectionSerializer
internal inline fun <reified K : Any, reified V : Any> patch(
    smallD: SmallDData,
    payload: K,
    path: String,
    crossinline acceptor: (V) -> Unit
) {
    val deferred = GlobalScope.async { smallD.smallD.patch(path, JSON.stringify(payload)) }
    deferred.invokeOnCompletion {
        if (it == null) {
            // TODO LOGGING
        } else {
            acceptor(JSON.parse(deferred.getCompleted()))
        }
    }
}

internal fun delete(smallD: SmallDData, path: String) {
    GlobalScope.launch { smallD.smallD.delete(path) }
}