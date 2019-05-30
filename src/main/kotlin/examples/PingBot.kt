package examples

import com.github.princesslana.smalldkt.bot
import com.github.princesslana.smalldkt.type.message.MessageCreateEvent
import com.github.princesslana.smalldkt.type.message.respond

fun main() {
    bot("~~ TOKEN ~~") {
        subscribe<MessageCreateEvent> {
            if (!event.author.isBot() && event.content == "++ping") {
                respond("Pong!")
            }
        }
    }
}