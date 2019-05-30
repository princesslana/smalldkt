package com.github.princesslana.smalldkt.type.channel

import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.Snowflake
import com.github.princesslana.smalldkt.delete
import com.github.princesslana.smalldkt.get

// Get Channel
// https://discordapp.com/developers/docs/resources/channel#get-channel

suspend fun SmallDData<*>.getChannel(channelId: Snowflake) =
    smallD.get("/channels/$channelId", Channel.serializer())

// Modify Channel // TODO more info needed
// https://discordapp.com/developers/docs/resources/channel#modify-channel

// Delete Channel // TODO unfinished
// https://discordapp.com/developers/docs/resources/channel#deleteclose-channel

fun SmallDData<*>.deleteChannel(channelId: Snowflake) =
    smallD.delete("/channels/$channelId")