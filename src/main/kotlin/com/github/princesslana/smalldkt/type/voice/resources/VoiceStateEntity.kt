package com.github.princesslana.smalldkt.type.voice.resources

import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.guild.resources.GuildMemberImpl
import kotlinx.serialization.Serializable

interface PartialVoiceState{
    val guild_id: Snowflake?
    val channel_id: Optional<Snowflake>?
    val user_id: Snowflake?
    val member: GuildMemberImpl?
    val session_id: String?
    val deaf: Boolean?
    val mute: Boolean?
    val self_deaf: Boolean?
    val self_mute: Boolean?
    val suppress: Boolean?
}

@Serializable
data class PartialVoiceStateImpl(
        override val guild_id: Snowflake? = null,
        override val channel_id: Optional<Snowflake>? = null,
        override val user_id: Snowflake? = null,
        override val member: GuildMemberImpl? = null,
        override val session_id: String? = null,
        override val deaf: Boolean? = null,
        override val mute: Boolean? = null,
        override val self_deaf: Boolean? = null,
        override val self_mute: Boolean? = null,
        override val suppress: Boolean? = null
): PartialVoiceState

interface VoiceState{
    val guild_id: Snowflake?
    val channel_id: Snowflake?
    val user_id: Snowflake
    val member: GuildMemberImpl?
    val session_id: String
    val deaf: Boolean
    val mute: Boolean
    val self_deaf: Boolean
    val self_mute: Boolean
    val suppress: Boolean
}

@Serializable
data class VoiceStateImpl(
        override val guild_id: Snowflake? = null,
        override val channel_id: Snowflake?,
        override val user_id: Snowflake,
        override val member: GuildMemberImpl? = null,
        override val session_id: String,
        override val deaf: Boolean,
        override val mute: Boolean,
        override val self_deaf: Boolean,
        override val self_mute: Boolean,
        override val suppress: Boolean
):VoiceState
