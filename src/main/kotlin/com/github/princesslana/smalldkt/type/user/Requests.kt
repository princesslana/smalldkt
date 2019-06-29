package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.http.*
import com.github.princesslana.smalldkt.http.delete
import com.github.princesslana.smalldkt.http.get
import com.github.princesslana.smalldkt.http.patch
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.channel.Channel
import com.github.princesslana.smalldkt.type.guild.PartialGuild
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.UnitSerializer

suspend fun SmallDData<*>.getMe() = smallD.get("/users/@me", User.serializer())
suspend fun SmallDData<*>.getUser(userId: Snowflake) = smallD.get("/user/$userId", User.serializer())

suspend fun SmallDData<*>.modifyMe(payload: ModifyCurrentUserPayload) =
    smallD.patch(payload, ModifyCurrentUserPayload.serializer(), "/users/@me", User.serializer())

@Serializable
data class ModifyCurrentUserPayload(val username: String? = null, val avatar: String? = null)

suspend fun SmallDData<*>.getCurrentUserGuilds(payload: GetCurrentUserGuildsPayload) =
    smallD.get("/users/@me/guilds${payload.queryString}", ArrayListSerializer(PartialGuild.serializer()))

class GetCurrentUserGuildsPayload(before: Snowflake? = null, after: Snowflake? = null, limit: Int? = null) {
    val queryString = queryString {
        if (before != null) +("before" to before)
        if (after != null) +("after" to after)
        if (limit != null) +("limit" to limit)
    }
}

suspend fun SmallDData<*>.leaveGuild(guildId: Snowflake) = smallD.delete("/users/@me/guilds/$guildId", UnitSerializer)
suspend fun SmallDData<*>.getUserDMs() = smallD.get("/users/@me/channels", ArrayListSerializer(Channel.serializer()))

suspend fun SmallDData<*>.createDM(payload: CreateDMPayload) =
    smallD.post(payload, CreateDMPayload.serializer(), "/users/@me/channels", Channel.serializer())

@Serializable
data class CreateDMPayload(val recipient_id: Snowflake)

suspend fun SmallDData<*>.createGroupDM(payload: CreateGroupDMPayload) =
    smallD.post(payload, CreateGroupDMPayload.serializer(), "/users/@me/channels", Channel.serializer())

@Serializable
data class CreateGroupDMPayload(val access_tokens: List<String>, val nicks: Map<Snowflake, String>)

suspend fun SmallDData<*>.getUserConnections() =
    smallD.get("/users/@me/connections", ArrayListSerializer(Connection.serializer()))