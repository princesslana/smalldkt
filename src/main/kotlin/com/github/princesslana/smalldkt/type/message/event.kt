package com.github.princesslana.smalldkt.type.message

import com.github.princesslana.smalldkt.ChannelEvent
import com.github.princesslana.smalldkt.Optional
import com.github.princesslana.smalldkt.Snowflake
import com.github.princesslana.smalldkt.TimeStamp
import com.github.princesslana.smalldkt.type.guild.PartialGuildMember
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://discordapp.com/developers/docs/topics/gateway#message-create
// Copy message to here for ease of access rather than wrapping it
@Serializable
class MessageCreateEvent(
    val id: Snowflake,
    @SerialName("channel_id") val channelId: Snowflake,
    @SerialName("guild_id") val guildId: Snowflake? = null,
    val author: User,
    val member: PartialGuildMember? = null,
    val content: String,
    val timestamp: TimeStamp,
    @SerialName("edited_timestamp") val editedTimestamp: TimeStamp?,
    val tts: Boolean,
    @SerialName("mention_everyone") val mentionEveryone: Boolean,
    val mentions: List<User>,
    @SerialName("mention_roles") val mentionRoles: List<Snowflake>,
    val attachments: List<MessageAttachment>,
    val embeds: List<Embed>,
    val reactions: List<Reaction>? = null,
    val nonce: Optional<Snowflake>? = Optional.absent(),
    val pinned: Boolean,
    @SerialName("webhook_id") val webhookId: Snowflake? = null,
    val type: MessageType,
    val activity: MessageActivity? = null,
    val application: MessageApplication? = null
) : ChannelEvent {
    override val eventChannel: Snowflake = channelId
}