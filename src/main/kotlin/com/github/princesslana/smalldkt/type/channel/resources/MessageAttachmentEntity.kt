package com.github.princesslana.smalldkt.type.channel.resources

import com.github.princesslana.smalldkt.type.Identifiable
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.Serializable

@Serializable(with = MessageAttachmentImpl.`$serializer`::class)
interface MessageAttachment : Identifiable {
    val filename: String
    val size: Int
    val url: String
    val proxy_url: String
    val height: Int?
    val width: Int?
}

@Serializable
data class MessageAttachmentImpl(
    override val filename: String,
    override val size: Int,
    override val url: String,
    override val proxy_url: String,
    override val height: Int?,
    override val width: Int?,
    override val id: Snowflake
) : MessageAttachment