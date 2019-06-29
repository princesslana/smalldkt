package com.github.princesslana.smalldkt.type.voice

import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.guild.GuildMember
import kotlinx.serialization.Serializable

interface VoiceStateFace {
    val guild_id: Snowflake? // optional
    val channel_id: Snowflake? // nullable
    val user_id: Snowflake
    val member: GuildMember? // optional
    val session_id: String
    val deaf: Boolean
    val mute: Boolean
    val self_deaf: Boolean
    val self_mute: Boolean
    val suppress: Boolean
}

@Serializable
data class VoiceState(
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
) : VoiceStateFace

@Serializable
data class PartialVoiceState(
    val guild_id: Snowflake? = null,
    val channel_id: Optional<Snowflake>? = Optional.absent(),
    val user_id: Snowflake? = null,
    val member: GuildMember? = null,
    val session_id: String? = null,
    val deaf: Boolean? = null,
    val mute: Boolean? = null,
    val self_deaf: Boolean? = null,
    val self_mute: Boolean? = null,
    val suppress: Boolean? = null
)