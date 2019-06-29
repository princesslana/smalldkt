package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.MessageEvent
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.channel.*
import com.github.princesslana.smalldkt.type.guild.PartialGuildMember
import com.github.princesslana.smalldkt.type.user.PartialGuildUser
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

@Serializable
data class MessageUpdateEvent(
    val id: Snowflake,
    override val channel_id: Snowflake,
    val guild_id: Snowflake? = null,
    val author: User? = null,
    val member: PartialGuildMember? = null,
    val content: String? = null,
    val timestamp: TimeStamp? = null,
    val edited_timestamp: Optional<TimeStamp>? = Optional.absent(),
    val tts: Boolean? = null,
    val mention_everyone: Boolean? = null,
    val mentions: List<PartialGuildUser>? = null,
    val mention_roles: List<Snowflake>? = null,
    val attachments: List<MessageAttachment>? = null,
    val embeds: List<Embed>? = null,
    val reactions: List<Reaction>? = null,
    val nonce: Optional<Snowflake>? = Optional.absent(),
    val pinned: Boolean? = null,
    val webhook_id: Snowflake? = null,
    val type: MessageType? = null,
    val activity: MessageActivity? = null,
    val application: MessageApplication? = null
) : MessageEvent {
    override val message_id: Snowflake = id
}