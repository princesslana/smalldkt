package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.Event
import com.github.princesslana.smalldkt.type.guild.UnavailableGuild
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.*
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class ReadyEvent(
    val v: Int,
    val user: User,
    val private_channels: List<Unit>,
    val guilds: List<UnavailableGuild>,
    val session_id: String,
    val _trace: List<String>,
    val shard: Shard?
) : Event

@Serializable(ShardSerializer::class)
data class Shard(val shardId: Int, val numShards: Int)

@Serializer(Shard::class)
class ShardSerializer : KSerializer<Shard> {
    override val descriptor = StringDescriptor.withName("ShardSerializer")

    override fun deserialize(decoder: Decoder): Shard {
        val list = decoder.decode(ArrayListSerializer(Int.serializer()))
        return Shard(list[0], list[1])
    }

    override fun serialize(encoder: Encoder, obj: Shard) {
        encoder.encode(ArrayListSerializer(Int.serializer()), listOf(obj.shardId, obj.numShards))
    }
}