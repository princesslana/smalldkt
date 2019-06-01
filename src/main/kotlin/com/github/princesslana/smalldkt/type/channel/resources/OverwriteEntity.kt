package com.github.princesslana.smalldkt.type.channel.resources

import com.github.princesslana.smalldkt.type.Identifiable
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable(with = OverwriteImpl.`$serializer`::class)
interface Overwrite : Identifiable {
    val type: String
    val allow: Int
    val deny: Int
}

@Serializable
data class OverwriteImpl(
    override val id: Snowflake,
    override val type: String,
    override val allow: Int,
    override val deny: Int
) : Overwrite