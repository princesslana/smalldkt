package com.github.princesslana.smalldkt.type.channel.events

import com.github.princesslana.smalldkt.type.*
import com.github.princesslana.smalldkt.type.channel.resources.*
import com.github.princesslana.smalldkt.type.guild.resources.PartialGuildMember
import com.github.princesslana.smalldkt.type.user.user.User
import kotlinx.serialization.Serializable

// Message Update
// https://discordapp.com/developers/docs/topics/gateway#message-update

@Serializable
data class MessageUpdateEvent(
    val guild_id: Snowflake? = null,
    val author: User? = null,
    val member: PartialGuildMember? = null,
    val content: String? = null,
    val timestamp: TimeStamp? = null,
    val edited_timestamp: TimeStamp?,
    val tts: Boolean? = null,
    val mention_everyone: Boolean? = null,
    val mentions: List<User>? = null,
    val mention_roles: List<Snowflake>? = null,
    val attachments: List<MessageAttachment>? = null,
    val embeds: List<Embed>? = null,
    val reactions: List<Reaction>? = null,
    val nonce: Optional<Snowflake>? = Optional.absent(),
    val pinned: Boolean? = null,
    val webhook_id: Snowflake? = null,
    val type: MessageType? = null,
    val activity: MessageActivity? = null,
    val application: MessageApplication? = null,
    override val id: Snowflake,
    override val channel_id: Snowflake
) : Identifiable, ChannelEvent