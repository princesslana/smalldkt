package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.ChannelEvent
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.channel.ChannelFace
import com.github.princesslana.smalldkt.type.channel.ChannelType
import com.github.princesslana.smalldkt.type.channel.Overwrite
import com.github.princesslana.smalldkt.type.user.User
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ChannelDeleteEvent(
    override val id: Snowflake,
    override val type: ChannelType,
    override val guild_id: Snowflake? = null,
    override val position: Int? = null,
    override val permission_overwrites: List<Overwrite>? = null,
    override val name: String? = null,
    override val topic: Optional<String>? = Optional.absent(),
    override val nsfw: Boolean? = null,
    override val last_message_id: Optional<Snowflake>? = Optional.absent(),
    override val bitrate: Int? = null,
    override val user_limit: Int? = null,
    override val rate_limit_per_user: Int? = null,
    override val recipients: List<User>? = null,
    override val icon: Optional<String>? = Optional.absent(),
    override val owner_id: Snowflake? = null,
    override val application_id: Snowflake? = null,
    override val parent_id: Optional<Snowflake>? = Optional.absent(),
    override val last_pin_timestamp: TimeStamp? = null
) : ChannelFace, ChannelEvent {
    @Transient
    override val channel_id: Snowflake = id
}