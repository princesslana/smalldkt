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

suspend fun getCurrentUser(smallDData: SmallDData): User = get(smallDData, "/users/@me", User.serializer())

// Get User
// https://discordapp.com/developers/docs/resources/user#get-user

suspend fun getUser(smallDData: SmallDData, userId: Snowflake): User =
    get(smallDData, "/users/${userId.value}", User.serializer())

// Modify Current User
// https://discordapp.com/developers/docs/resources/user#modify-current-user

@Serializable
data class ModifyCurrentUserPayload(
    val username: String,
    val avatar: AvatarData
)

suspend fun modifyCurrentUser(smallDData: SmallDData, payload: ModifyCurrentUserPayload): User =
    patch(smallDData, payload, ModifyCurrentUserPayload.serializer(), "/users/@me", User.serializer())

// Get Current User Guilds
// https://discordapp.com/developers/docs/resources/user#get-current-user-guilds

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
}, ArrayListSerializer(PartialGuild.serializer()))

// Leave Guild
// https://discordapp.com/developers/docs/resources/user#leave-guild

fun leaveGuild(smallDData: SmallDData, guildId: Snowflake) =
    delete(smallDData, "/users/@me/guilds/${guildId.value}")

// Get User DMs
// https://discordapp.com/developers/docs/resources/user#get-user-dms

suspend fun getUserDMs(smallDData: SmallDData): List<Channel> =
    get(smallDData, "/users/@me/channels", ArrayListSerializer(Channel.serializer()))

// Create DM
// https://discordapp.com/developers/docs/resources/user#create-dm

@Serializable
data class CreateDMPayload(@SerialName("recipient_id") val recipientId: Snowflake)

suspend fun createDM(smallDData: SmallDData, payload: CreateDMPayload): Channel =
    post(smallDData, payload, CreateDMPayload.serializer(), "/users/@me/channels", Channel.serializer())

// Create Group DM
// https://discordapp.com/developers/docs/resources/user#create-group-dm

@Serializable
data class CreateGroupDMPayload(@SerialName("access_tokens") val accessTokens: List<String>, val nicks: Map<Snowflake, String>)

suspend fun createGroupDM(smallDData: SmallDData, payload: CreateGroupDMPayload): Channel =
    post(smallDData, payload, CreateGroupDMPayload.serializer(), "/users/@me/channels", Channel.serializer())

// Get User Connections
// https://discordapp.com/developers/docs/resources/user#get-user-connections

suspend fun getUserConnections(smallDData: SmallDData): List<Connection> =
    get(smallDData, "/users/@me/connections", ArrayListSerializer(Connection.serializer()))