package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.Event
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.guild.GuildMember
import com.github.princesslana.smalldkt.type.voice.VoiceStateFace
import kotlinx.serialization.Serializable

@Serializable
data class VoiceStateUpdateEvent(
    override val guild_id: Snowflake? = null,
    override val channel_id: Snowflake?,
    override val user_id: Snowflake,
    override val member: GuildMember? = null,
    override val session_id: String,
    override val deaf: Boolean,
    override val mute: Boolean,
    override val self_deaf: Boolean,
    override val self_mute: Boolean,
    override val suppress: Boolean
) : VoiceStateFace, Event