package com.github.princesslana.smalldkt.type.guild.resources

import com.github.princesslana.smalldkt.type.Identifiable
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.TimeStamp
import com.github.princesslana.smalldkt.type.channel.resources.ChannelImpl
import com.github.princesslana.smalldkt.type.emoji.resources.EmojiImpl
import com.github.princesslana.smalldkt.type.gateway.resources.PartialPresenceUpdateImpl
import com.github.princesslana.smalldkt.type.voice.resources.PartialVoiceStateImpl
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

interface Role: Identifiable{
    val name: String
    val color: Int
    val hoist: Boolean
    val position: Int
    val permissions: Int
    val managed: Boolean
    val mentionable: Boolean
}

@Serializable
data class RoleImpl(
        override val id: Snowflake,
        override val name: String,
        override val color: Int,
        override val hoist: Boolean,
        override val position: Int,
        override val permissions: Int,
        override val managed: Boolean,
        override val mentionable: Boolean
): Role

interface PartialGuild{
    val id: Snowflake?
    val name: String?
    val icon: Optional<String>?
    val splash: Optional<String>?
    val owner: Boolean?
    val owner_id: Snowflake?
    val permissions: Int?
    val region: String?
    val afk_channel_id: Optional<Snowflake>?
    val afk_timeout: Int?
    val embed_enabled: Boolean?
    val embed_channel_id: Snowflake?
    val verification_level: VerificationLevel?
    val default_message_notifactions: Int?
    val explicit_content_filter: Int?
    val roles: List<RoleImpl>?
    val emojis: List<EmojiImpl>?
    val features: List<String>?
    val mfa_level: MFALevel?
    val application_id: Optional<Snowflake>?
    val widget_enabled: Boolean?
    val widget_channel_id: Snowflake?
    val system_channel_id: Optional<Snowflake>?
    val joined_at: TimeStamp?
    val large: Boolean?
    val unavailable: Boolean?
    val member_count: Int?
    val voice_states: List<PartialVoiceStateImpl>?
    val members: List<GuildMemberImpl>?
    val channels: List<ChannelImpl>?
    val presences: List<PartialPresenceUpdateImpl>?
    val max_presences: Optional<Int>?
    val max_members: Int?
    val vanity_url_code: Optional<String>?
    val description: Optional<String>?
    val banner: Optional<String>?
    val premium_tier: PremiumTier?
    val premium_subscription_count: Int?
}

@Serializable
data class PartialGuildImpl(
        override val id: Snowflake? = null,
        override val name: String? = null,
        override val icon: Optional<String>? = Optional.absent(),
        override val splash: Optional<String>? = Optional.absent(),
        override val owner: Boolean? = null,
        override val owner_id: Snowflake? = null,
        override val permissions: Int? = null,
        override val region: String? = null,
        override val afk_channel_id: Optional<Snowflake>? = Optional.absent(),
        override val afk_timeout: Int? = null,
        override val embed_enabled: Boolean? = null,
        override val embed_channel_id: Snowflake? = null,
        override val verification_level: VerificationLevel? = null,
        override val default_message_notifactions: Int? = null,
        override val explicit_content_filter: Int? = null,
        override val roles: List<RoleImpl>? = null,
        override val emojis: List<EmojiImpl>? = null,
        override val features: List<String>? = null,
        override val mfa_level: MFALevel? = null,
        override val application_id: Optional<Snowflake>? = Optional.absent(),
        override val widget_enabled: Boolean? = null,
        override val widget_channel_id: Snowflake? = null,
        override val system_channel_id: Optional<Snowflake>? = Optional.absent(),
        override val joined_at: TimeStamp? = null,
        override val large: Boolean? = null,
        override val unavailable: Boolean? = null,
        override val member_count: Int? = null,
        override val voice_states: List<PartialVoiceStateImpl>? = null,
        override val members: List<GuildMemberImpl>? = null,
        override val channels: List<ChannelImpl>? = null,
        override val presences: List<PartialPresenceUpdateImpl>? = null,
        override val max_presences: Optional<Int>? = null,
        override val max_members: Int? = null,
        override val vanity_url_code: Optional<String>? = Optional.absent(),
        override val description: Optional<String>? = Optional.absent(),
        override val banner: Optional<String>? = Optional.absent(),
        override val premium_tier: PremiumTier? = null,
        override val premium_subscription_count: Int? = null
): PartialGuild


@Serializable(with = VerificationLevelSerializer::class)
enum class VerificationLevel(val level: Int){
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4)
}

@Serializer(forClass = VerificationLevel::class)
class VerificationLevelSerializer: KSerializer<VerificationLevel>{
    override val descriptor: SerialDescriptor =
            StringDescriptor.withName("VerificationLevelSerializer")

    override fun deserialize(decoder: Decoder): VerificationLevel {
        return when(decoder.decodeInt()){
            0 -> VerificationLevel.NONE
            1 -> VerificationLevel.LOW
            2 -> VerificationLevel.MEDIUM
            3 -> VerificationLevel.HIGH
            4 -> VerificationLevel.VERY_HIGH
            else -> throw IllegalStateException("Illegal Verification level.")
        }
    }

    override fun serialize(encoder: Encoder, obj: VerificationLevel) {
        encoder.encodeInt(obj.level)
    }
}

@Serializable(with = MFALevelSerializer::class)
enum class MFALevel(val level: Int){
    NONE(0),
    ELEVATED(1)
}

@Serializer(forClass = MFALevel::class)
class MFALevelSerializer: KSerializer<MFALevel>{
    override val descriptor: SerialDescriptor =
            StringDescriptor.withName("MFALevelSerializer")

    override fun deserialize(decoder: Decoder): MFALevel {
        return when(decoder.decodeInt()){
            0 -> MFALevel.NONE
            1 -> MFALevel.ELEVATED
            else -> throw IllegalStateException("Illegal MFA Level.")
        }
    }

    override fun serialize(encoder: Encoder, obj: MFALevel) {
        encoder.encodeInt(obj.level)
    }
}

@Serializable(with = NotificationLevelSerializer::class)
enum class NotificationLevel(val level: Int){
    ALL_MESSAGES(0),
    ONLY_MENTIONS(1)
}

@Serializer(forClass = NotificationLevel::class)
class NotificationLevelSerializer: KSerializer<NotificationLevel>{
    override val descriptor: SerialDescriptor =
            StringDescriptor.withName("NotificationLevelSerializer")

    override fun deserialize(decoder: Decoder): NotificationLevel {
        return when(decoder.decodeInt()){
            0 -> NotificationLevel.ALL_MESSAGES
            1 -> NotificationLevel.ONLY_MENTIONS
            else -> throw IllegalStateException("Illegal Notification Level.")
        }
    }

    override fun serialize(encoder: Encoder, obj: NotificationLevel) {
        encoder.encodeInt(obj.level)
    }
}

@Serializable(with = ExplicitContentFilterLevelSerializer::class)
enum class ExplicitContentFilterLevel(val level: Int){
    DISALED(0),
    MEMBERS_WITOUT_ROLES(1),
    ALL_MEMBERS(2)
}

@Serializer(forClass = ExplicitContentFilterLevel::class)
class ExplicitContentFilterLevelSerializer: KSerializer<ExplicitContentFilterLevel>{
    override val descriptor: SerialDescriptor =
            StringDescriptor.withName("ExcplicitContentFilterLevelSerializer")

    override fun deserialize(decoder: Decoder): ExplicitContentFilterLevel {
        return when(decoder.decodeInt()){
            0 -> ExplicitContentFilterLevel.DISALED
            1 -> ExplicitContentFilterLevel.MEMBERS_WITOUT_ROLES
            2 -> ExplicitContentFilterLevel.ALL_MEMBERS
            else -> throw IllegalStateException("Illegal Content Filter Level.")
        }
    }

    override fun serialize(encoder: Encoder, obj: ExplicitContentFilterLevel) {
        encoder.encodeInt(obj.level)
    }
}

@Serializable(with = PremiumTierSerializer::class)
enum class PremiumTier(val level: Int){
    NONE(0),
    TIER_1(1),
    TIER_2(2),
    TIER_3(3)
}

@Serializer(forClass = PremiumTier::class)
class PremiumTierSerializer: KSerializer<PremiumTier>{
    override val descriptor: SerialDescriptor =
            StringDescriptor.withName("PremiumTierSerializer")

    override fun deserialize(decoder: Decoder): PremiumTier {
        return when(decoder.decodeInt()){
            0 -> PremiumTier.NONE
            1 -> PremiumTier.TIER_1
            2 -> PremiumTier.TIER_2
            3 -> PremiumTier.TIER_3
            else -> throw IllegalStateException("Illegal Premium Tier.")
        }
    }

    override fun serialize(encoder: Encoder, obj: PremiumTier) {
        encoder.encodeInt(obj.level)
    }
}
