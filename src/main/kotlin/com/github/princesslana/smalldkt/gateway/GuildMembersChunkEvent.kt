package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.guild.GuildMember
import kotlinx.serialization.Serializable

@Serializable
data class GuildMembersChunkEvent(
    override val guild_id: Snowflake,
    val members: List<GuildMember>
) : GuildEvent