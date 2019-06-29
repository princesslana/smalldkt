package com.github.princesslana.smalldkt.type.emoji

import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.http.delete
import com.github.princesslana.smalldkt.http.get
import com.github.princesslana.smalldkt.http.patch
import com.github.princesslana.smalldkt.http.post
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.UnitSerializer

suspend fun SmallDData<*>.listGuildEmojis(guildId: Snowflake) =
    smallD.get("/guilds/$guildId/emojis", ArrayListSerializer(Emoji.serializer()))

suspend fun SmallDData<*>.getGuildEmoji(guildId: Snowflake, emojiId: Snowflake) =
    smallD.get("/guilds/$guildId/emojis/$emojiId", Emoji.serializer())

suspend fun SmallDData<*>.createGuildEmoji(guildId: Snowflake, payload: CreateGuildEmojiPayload) =
    smallD.post(payload, CreateGuildEmojiPayload.serializer(), "/guilds/$guildId/emojis", Emoji.serializer())

@Serializable
data class CreateGuildEmojiPayload(val name: String, val image: String, val roles: List<Snowflake>)

suspend fun SmallDData<*>.modifyGuildEmoji(guildId: Snowflake, emojiId: Snowflake, payload: ModifyGuildEmojiPayload) =
    smallD.patch(payload, ModifyGuildEmojiPayload.serializer(), "/guilds/$guildId/emojis/$emojiId", Emoji.serializer())

@Serializable
data class ModifyGuildEmojiPayload(val name: String, val roles: List<Snowflake>)

suspend fun SmallDData<*>.deleteGuildEmoji(guildId: Snowflake, emojiId: Snowflake) =
    smallD.delete("/guilds/$guildId/emojis/$emojiId", UnitSerializer)