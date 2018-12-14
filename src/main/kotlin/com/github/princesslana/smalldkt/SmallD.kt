package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.SmallD as Bot

fun startBot(token: String, receiver: Bot.() -> Unit, runner: Bot.() -> Unit) = Bot.create(token).use {
    it.receiver()
    it.runner()
    it
}

fun startBot(token: String, receiver: Bot.() -> Unit) = startBot(token, receiver, Bot::run)

fun startBotConnect(token: String, receiver: Bot.() -> Unit) =
    startBot(token, receiver, Bot::connect)

fun startBotAwait(token: String, receiver: Bot.() -> Unit) =
    startBot(token, receiver, Bot::await)