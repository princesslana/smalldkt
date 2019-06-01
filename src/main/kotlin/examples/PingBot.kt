package examples

import com.github.princesslana.smalldkt.bot
import com.github.princesslana.smalldkt.type.channel.events.MessageCreateEvent
import com.github.princesslana.smalldkt.type.channel.respond

fun main() {
    bot("~~ TOKEN ~~") {
        subscribe<MessageCreateEvent> {
            if (!event.author.isBot() && event.content == "++ping") {
                respond("Pong!")
            }
        }
    }
}