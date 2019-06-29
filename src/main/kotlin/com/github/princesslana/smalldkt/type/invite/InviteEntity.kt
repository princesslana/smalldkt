package com.github.princesslana.smalldkt.type.invite

import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.channel.PartialChannel
import com.github.princesslana.smalldkt.type.guild.PartialGuild
import com.github.princesslana.smalldkt.type.user.PartialUser
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable

interface InviteFace {
    val code: String
    val guild: PartialGuild? // optional
    val channel: PartialChannel
    val target_user: PartialUser
    val target_user_type: Int? // optional
    val approximate_presence_count: Int? // optional
    val approximate_member_cound: Int? // optional
}

@Serializable
data class Invite(
    override val code: String,
    override val guild: PartialGuild? = null,
    override val channel: PartialChannel,
    override val target_user: PartialUser,
    override val target_user_type: Int? = null,
    override val approximate_presence_count: Int? = null,
    override val approximate_member_cound: Int? = null
) : InviteFace

@Serializable
data class InviteWithMetadata(
    override val code: String,
    override val guild: PartialGuild? = null,
    override val channel: PartialChannel,
    override val target_user: PartialUser,
    override val target_user_type: Int? = null,
    override val approximate_presence_count: Int? = null,
    override val approximate_member_cound: Int? = null,
    val inviter: User,
    val uses: Int,
    val max_uses: Int,
    val max_age: Int,
    val temporary: Boolean,
    val created_at: TimeStamp,
    val revoked: Boolean
) : InviteFace