package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.*
import com.github.princesslana.smalldkt.get
import com.github.princesslana.smalldkt.patch
import com.github.princesslana.smalldkt.type.guild.PartialGuild
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable

// Get Current User
// https://discordapp.com/developers/docs/resources/user#get-current-user

@UseExperimental(ImplicitReflectionSerializer::class)
fun getCurrentUser(smallDData: SmallDData, callback: (User) -> Unit = {}) {
    get(smallDData, "/users/@me", callback)
}

// Get User
// https://discordapp.com/developers/docs/resources/user#get-user

class GetUser(userId: Snowflake, private val callback: (User) -> Unit = {}) : Request {
    override val path = "/users/${userId.value}"

    @UseExperimental(ImplicitReflectionSerializer::class)
    override fun executeRequest(smallDData: SmallDData) {
        get(smallDData, path, callback)
    }
}

// Modify Current User
// https://discordapp.com/developers/docs/resources/user#modify-current-user

@Serializable
data class ModifyCurrentUserPayload(
    val username: String,
    val avatar: AvatarData
)

class ModifyCurrentUser(private val payload: ModifyCurrentUserPayload, private val callback: (User) -> Unit = {}) :
    Request {
    override val path = "/users/@me"

    @UseExperimental(ImplicitReflectionSerializer::class)
    override fun executeRequest(smallDData: SmallDData) {
        patch(smallDData, payload, path, callback)
    }
}

// Get Current User Guilds
// https://discordapp.com/developers/docs/resources/user#get-current-user-guilds

class GetCurrentUserGuilds(
    private val before: Optional<Snowflake> = Optional.absent(),
    private val after: Optional<Snowflake> = Optional.absent(),
    private val limit: Optional<Int> = Optional.absent(),
    private val callback: (List<PartialGuild>) -> Unit
) : Request {
    override val path: String = "/users/@me/guilds/" + queryString {
        before.ifPresent { +("before" to "${it.value}") }
        after.ifPresent { +("after" to "${it.value}") }
        limit.ifPresent { +("limit" to "$it") }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    override fun executeRequest(smallDData: SmallDData) {
        get(smallDData, path, callback)
    }
}