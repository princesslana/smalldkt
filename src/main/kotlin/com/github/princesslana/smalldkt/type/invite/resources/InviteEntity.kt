package com.github.princesslana.smalldkt.type.invite.resources

import com.github.princesslana.smalldkt.type.user.user.User
import kotlinx.serialization.*
import com.github.princesslana.smalldkt.type.channel.resources.*
import kotlinx.serialization.Serializable


@Serializable
data class GuildObjectStub(
        val splash: String?,
        val features: List<String>,
        val name: String,
        val verification_level: Int,
        val icon: String?,
        val banner: String?,
        val id: String,
        val vanity_url_code: String?,
        val description: String?
)

@Serializable
data class InviteData(
        val inviter: User,
        val code: String,
        val guild: GuildObjectStub,
        val channel: Channel
)
