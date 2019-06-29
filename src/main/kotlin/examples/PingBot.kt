package examples

import com.github.princesslana.smalldkt.bot
import com.github.princesslana.smalldkt.gateway.MessageCreateEvent
import com.github.princesslana.smalldkt.type.channel.respond

fun main() {
    bot("~~ TOKEN ~~") {
        subscribe<MessageCreateEvent> {
            if (event.author.bot == true || event.content != "++ping") return@subscribe
            respond("pong!")
        }
    }
}