package com.github.princesslana.smalldkt.type.guild.resources

import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.user.user.User
import kotlinx.serialization.Serializable

@Serializable(with = PartialGuildMemberImpl.`$serializer`::class)
interface PartialGuildMember {
    val nick: String?
    val roles: List<Snowflake>
    val joined_at: TimeStamp
    val deaf: Boolean
    val mute: Boolean
}

@Serializable
data class PartialGuildMemberImpl(
    override val nick: String? = null,
    override val roles: List<Snowflake>,
    override val joined_at: TimeStamp,
    override val deaf: Boolean,
    override val mute: Boolean
) : PartialGuildMember

@Serializable(with = GuildMemberImpl.`$serializer`::class)
interface GuildMember : PartialGuildMember {
    val user: User
}

@Serializable
data class GuildMemberImpl(
    override val nick: String? = null,
    override val roles: List<Snowflake>,
    override val joined_at: TimeStamp,
    override val deaf: Boolean,
    override val mute: Boolean,
    override val user: User
) : GuildMember