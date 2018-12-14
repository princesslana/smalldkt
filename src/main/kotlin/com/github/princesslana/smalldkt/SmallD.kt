package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.SmallD as Bot

fun startBot(token: String, receiver: Bot.() -> Unit) = Bot.create(token).use {
    it.receiver()
    it.run()
    it
}