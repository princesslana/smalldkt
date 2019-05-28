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
suspend fun getCurrentUser(smallDData: SmallDData): User = get(smallDData, "/users/@me")

// Get User
// https://discordapp.com/developers/docs/resources/user#get-user

@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun getUser(smallDData: SmallDData, userId: Snowflake): User = get(smallDData, "/users/${userId.value}")

// Modify Current User
// https://discordapp.com/developers/docs/resources/user#modify-current-user

@Serializable
data class ModifyCurrentUserPayload(
    val username: String,
    val avatar: AvatarData
)

@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun modifyCurrentUser(smallDData: SmallDData, payload: ModifyCurrentUserPayload): User =
    patch(smallDData, payload, "/users/@me")

// Get Current User Guilds
// https://discordapp.com/developers/docs/resources/user#get-current-user-guilds

@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun getCurrentUserGuilds(
    smallDData: SmallDData,
    before: Optional<Snowflake> = Optional.absent(),
    after: Optional<Snowflake> = Optional.absent(),
    limit: Optional<Int> = Optional.absent()
): List<PartialGuild> = get(smallDData, "/users/@me/guilds/" + queryString {
    before.ifPresent { +("before" to "${it.value}") }
    after.ifPresent { +("after" to "${it.value}") }
    limit.ifPresent { +("limit" to "$it") }
})