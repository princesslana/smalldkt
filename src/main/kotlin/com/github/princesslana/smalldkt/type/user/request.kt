package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.*
import com.github.princesslana.smalldkt.get
import com.github.princesslana.smalldkt.patch
import com.github.princesslana.smalldkt.type.channel.Channel
import com.github.princesslana.smalldkt.type.guild.PartialGuild
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.SerialName
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
    before: Snowflake? = null,
    after: Snowflake? = null,
    limit: Int? = null
): List<PartialGuild> = get(smallDData, "/users/@me/guilds/" + queryString {
    if (before != null) {
        +("before" to "$before")
    }
    if (after != null) {
        +("after" to "$after")
    }
    if (limit != null) {
        +("limit" to "$limit")
    }
})

// Leave Guild
// https://discordapp.com/developers/docs/resources/user#leave-guild

fun leaveGuild(smallDData: SmallDData, guildId: Snowflake) =
    delete(smallDData, "/users/@me/guilds/${guildId.value}")

// Get User DMs
// https://discordapp.com/developers/docs/resources/user#get-user-dms

@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun getUserDMs(smallDData: SmallDData): List<Channel> = get(smallDData, "/users/@me/channels")

// Create DM
// https://discordapp.com/developers/docs/resources/user#create-dm

@Serializable
data class CreateDMPayload(@SerialName("recipient_id") val recipientId: Snowflake)

@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun createDM(smallDData: SmallDData, payload: CreateDMPayload): Channel =
    post(smallDData, payload, "/users/@me/channels")

// Create Group DM
// https://discordapp.com/developers/docs/resources/user#create-group-dm

@Serializable
data class CreateGroupDMPayload(@SerialName("access_tokens") val accessTokens: List<String>, val nicks: Map<Snowflake, String>)

@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun createGroupDM(smallDData: SmallDData, payload: CreateGroupDMPayload): Channel =
    post(smallDData, payload, "/users/@me/channels")

// Get User Connections
// https://discordapp.com/developers/docs/resources/user#get-user-connections

@UseExperimental(ImplicitReflectionSerializer::class)
suspend fun getUserConnections(smallDData: SmallDData): List<Connection> = get(smallDData, "/users/@me/connections")