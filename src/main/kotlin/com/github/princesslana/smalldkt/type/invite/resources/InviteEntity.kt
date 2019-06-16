package com.github.princesslana.smalldkt.type.invite.resources

import com.github.princesslana.smalldkt.type.channel.resources.*
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.guild.resources.PartialGuildImpl
import com.github.princesslana.smalldkt.type.user.user.UserImpl
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor


interface InviteMetadata{
    val inviter: Optional<UserImpl>
    val uses: Optional<Int>
    val max_uses: Optional<Int>
    val max_age: Optional<Int>
    val temporary: Optional<Boolean>
    val created_at: Optional<TimeStamp>
    val revoked: Optional<Boolean>
}

interface Invite{
    val code: String
    val guild: Optional<PartialGuildImpl>
    val channel: ChannelImpl
    val target_user: Optional<UserImpl>
    val target_user_type: Optional<TargetUserType>
    val approximate_presence_count: Optional<Int>
    val approximate_member_count: Optional<Int>
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
        override val guild: Optional<PartialGuildImpl> = Optional.absent(),
        override val channel: ChannelImpl,
        override val target_user: Optional<UserImpl> = Optional.absent(),
        override val target_user_type: Optional<TargetUserType> = Optional.absent(),
        override val approximate_presence_count: Optional<Int> = Optional.absent(),
        override val approximate_member_count: Optional<Int> = Optional.absent(),
        override val inviter: Optional<UserImpl> = Optional.absent(),
        override val uses: Optional<Int> = Optional.absent(),
        override val max_uses: Optional<Int> = Optional.absent(),
        override val max_age: Optional<Int> = Optional.absent(),
        override val temporary: Optional<Boolean> = Optional.absent(),
        override val created_at: Optional<TimeStamp> = Optional.absent(),
        override val revoked: Optional<Boolean> = Optional.absent()
): Invite,InviteMetadata