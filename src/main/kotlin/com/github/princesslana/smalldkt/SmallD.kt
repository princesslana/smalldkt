package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.SmallD

private fun acceptBot(token: String, receiver: SmallD.() -> Unit): SmallD {
    val smallD = SmallD.create(token)
    receiver(smallD)
    return smallD
}

fun startBot(token: String, receiver: SmallD.() -> Unit) = acceptBot(token, receiver).run()

fun startBotConnect(token: String, receiver: SmallD.() -> Unit): SmallD {
    val smallD = acceptBot(token, receiver)
    smallD.connect()
    return smallD
}

fun startBotAwait(token: String, receiver: SmallD.() -> Unit): SmallD {
    val smallD = acceptBot(token, receiver)
    smallD.await()
    return smallD
}