package com.github.princesslana.smalldkt.type.channel.events

import com.github.princesslana.smalldkt.type.ChannelEvent
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.channel.resources.*
import com.github.princesslana.smalldkt.type.guild.resources.PartialGuildMember
import com.github.princesslana.smalldkt.type.user.user.User
import kotlinx.serialization.Serializable

// Message Create
// https://discordapp.com/developers/docs/topics/gateway#message-create

@Serializable
data class MessageCreateEvent(
    override val guild_id: Snowflake? = null,
    override val author: User,
    override val member: PartialGuildMember? = null,
    override val content: String,
    override val timestamp: TimeStamp,
    override val edited_timestamp: TimeStamp?,
    override val tts: Boolean,
    override val mention_everyone: Boolean,
    override val mentions: List<User>,
    override val mention_roles: List<Snowflake>,
    override val attachments: List<MessageAttachment>,
    override val embeds: List<Embed>,
    override val reactions: List<Reaction>? = null,
    override val nonce: Optional<Snowflake>? = Optional.absent(),
    override val pinned: Boolean,
    override val webhook_id: Snowflake? = null,
    override val type: MessageType,
    override val activity: MessageActivity? = null,
    override val application: MessageApplication? = null,
    override val id: Snowflake,
    override val channel_id: Snowflake
) : Message, ChannelEvent