package com.github.princesslana.smalldkt.examples

import com.eclipsesource.json.Json
import com.github.princesslana.smalldkt.startBot

fun main(args: Array<String>) {
    startBot(System.getProperty("token")) {
        onGatewayPayload {
            val json = Json.parse(it).asObject()

            if (json.getInt("op", -1) == 0 && json.getString("t", "") == "MESSAGE_CREATE") {
                val data = json.get("d").asObject()
                when (data.getString("content", "")) {
                    "++ping" -> post(
                        "/channels/${data.getString("channel_id", "null")}/messages",
                        Json.`object`().add("content", "Pong").toString()
                    )
                }
            }
        }
    }
}