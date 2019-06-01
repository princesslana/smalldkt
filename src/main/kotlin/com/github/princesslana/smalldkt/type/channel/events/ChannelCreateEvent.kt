package com.github.princesslana.smalldkt.type.channel.events

import com.github.princesslana.smalldkt.type.Event
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.channel.resources.Channel
import com.github.princesslana.smalldkt.type.channel.resources.ChannelType
import com.github.princesslana.smalldkt.type.channel.resources.Overwrite
import com.github.princesslana.smalldkt.type.user.user.User
import kotlinx.serialization.Serializable

// Channel Create
// https://discordapp.com/developers/docs/topics/gateway#channel-create

@Serializable
data class ChannelCreateEvent(
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
    override val last_pin_timestamp: TimeStamp?,
    override val id: Snowflake
) : Channel, Event