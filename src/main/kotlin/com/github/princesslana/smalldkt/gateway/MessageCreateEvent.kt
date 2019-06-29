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
import kotlinx.serialization.Transient

@Serializable
data class MessageCreateEvent(
    override val id: Snowflake,
    override val channel_id: Snowflake,
    override val guild_id: Snowflake? = null,
    override val author: User,
    override val member: PartialGuildMember? = null,
    override val content: String,
    override val timestamp: TimeStamp,
    override val edited_timestamp: TimeStamp?,
    override val tts: Boolean,
    override val mention_everyone: Boolean,
    override val mentions: List<PartialGuildUser>,
    override val mention_roles: List<Snowflake>,
    override val attachments: List<Attachment>,
    override val embeds: List<Embed>,
    override val reactions: List<Reaction>? = null,
    override val nonce: Optional<Snowflake>? = Optional.absent(),
    override val pinned: Boolean,
    override val webhook_id: Snowflake? = null,
    override val type: MessageType,
    override val activity: MessageActivity? = null,
    override val application: MessageApplication? = null
) : MessageFace, MessageEvent {
    override val message_id: Snowflake = id
}