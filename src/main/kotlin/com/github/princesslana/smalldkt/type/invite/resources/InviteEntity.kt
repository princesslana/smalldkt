package com.github.princesslana.smalldkt.type.invite.resources

import com.github.princesslana.smalldkt.type.channel.resources.*
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.guild.resources.PartialGuildImpl
import com.github.princesslana.smalldkt.type.user.user.UserImpl
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor


interface InviteMetadata{
    val inviter: UserImpl?
    val uses: Int?
    val max_uses: Int?
    val max_age: Int?
    val temporary: Boolean?
    val created_at: TimeStamp?
    val revoked: Boolean?
}

interface Invite{
    val code: String
    val guild: PartialGuildImpl?
    val channel: ChannelImpl
    val target_user: UserImpl?
    val target_user_type: TargetUserType?
    val approximate_presence_count: Int?
    val approximate_member_count: Int?
}

@Serializable(with = TargetUserTypeSerializer::class)
enum class TargetUserType(val type: Int){
    STREAM(1)
}

@Serializer(forClass = TargetUserType::class)
class TargetUserTypeSerializer: KSerializer<TargetUserType>{
    override val descriptor: SerialDescriptor = StringDescriptor.withName("TargetUserTypeSerializer")
    override fun deserialize(decoder: Decoder): TargetUserType {
        return when (decoder.decodeInt()){
            1 -> TargetUserType.STREAM
            else -> throw IllegalStateException("Illegal Target user type.")
        }
    }
    override fun serialize(encoder: Encoder, obj: TargetUserType) {
        encoder.encodeInt(obj.type)
    }
}

@Serializable
data class InviteImpl(
        override val code: String,
        override val guild: PartialGuildImpl? = null,
        override val channel: ChannelImpl,
        override val target_user: UserImpl? = null,
        override val target_user_type: TargetUserType? = null,
        override val approximate_presence_count: Int? = null,
        override val approximate_member_count: Int? = null,
        override val inviter: UserImpl? = null,
        override val uses: Int? = null,
        override val max_uses: Int? = null,
        override val max_age: Int? = null,
        override val temporary: Boolean? = null,
        override val created_at: TimeStamp? = null,
        override val revoked: Boolean? = null
): Invite,InviteMetadata