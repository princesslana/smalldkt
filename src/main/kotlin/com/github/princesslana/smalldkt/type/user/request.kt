@file:Suppress("unused")

package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.*
import com.github.princesslana.smalldkt.type.channel.Channel
import com.github.princesslana.smalldkt.type.guild.PartialGuild
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.ArrayListSerializer

// Get Current User
// https://discordapp.com/developers/docs/resources/user#get-current-user

suspend fun SmallDData<*>.getCurrentUser() = smallD.get("/users/@me", User.serializer())

// Get User
// https://discordapp.com/developers/docs/resources/user#get-user

suspend fun SmallDData<*>.getUser(userId: Snowflake) =
    smallD.get("/users/$userId", User.serializer())

// Modify Current User
// https://discordapp.com/developers/docs/resources/user#modify-current-user

@Serializable
data class ModifyCurrentUserPayload(
    val username: String,
    val avatar: AvatarData
)

suspend fun SmallDData<*>.modifyCurrentUser(payload: ModifyCurrentUserPayload) =
    smallD.patch(payload, ModifyCurrentUserPayload.serializer(), "/users/@me", User.serializer())

// Get Current User Guilds
// https://discordapp.com/developers/docs/resources/user#get-current-user-guilds

suspend fun SmallDData<*>.getCurrentUserGuilds(
    before: Snowflake? = null,
    after: Snowflake? = null,
    limit: Int? = null
) = smallD.get("/users/@me/guilds/" + queryString {
    if (before != null) {
        +("before" to "$before")
    }
    if (after != null) {
        +("after" to "$after")
    }
    if (limit != null) {
        +("limit" to "$limit")
    }
}, ArrayListSerializer(PartialGuild.serializer()))

// Leave Guild
// https://discordapp.com/developers/docs/resources/user#leave-guild

fun SmallDData<*>.leaveGuild(guildId: Snowflake) =
    smallD.delete("/users/@me/guilds/$guildId")

// Get User DMs
// https://discordapp.com/developers/docs/resources/user#get-user-dms

suspend fun SmallDData<*>.getUserDMs() =
    smallD.get("/users/@me/channels", ArrayListSerializer(Channel.serializer()))

// Create DM
// https://discordapp.com/developers/docs/resources/user#create-dm

@Serializable
data class CreateDMPayload(@SerialName("recipient_id") val recipientId: Snowflake)

suspend fun SmallDData<*>.createDM(payload: CreateDMPayload) =
    smallD.post(payload, CreateDMPayload.serializer(), "/users/@me/channels", Channel.serializer())

// Create Group DM
// https://discordapp.com/developers/docs/resources/user#create-group-dm

@Serializable
data class CreateGroupDMPayload(@SerialName("access_tokens") val accessTokens: List<String>, val nicks: Map<Snowflake, String>)

suspend fun SmallDData<*>.createGroupDM(payload: CreateGroupDMPayload) =
    smallD.post(payload, CreateGroupDMPayload.serializer(), "/users/@me/channels", Channel.serializer())

// Get User Connections
// https://discordapp.com/developers/docs/resources/user#get-user-connections

suspend fun SmallDData<*>.getUserConnections() =
    smallD.get("/users/@me/connections", ArrayListSerializer(Connection.serializer()))