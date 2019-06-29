package com.github.princesslana.smalldkt.type.guild

import com.github.princesslana.smalldkt.permissions.PermissionBase
import com.github.princesslana.smalldkt.permissions.Role
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.emoji.Emoji
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

interface GuildFace {
    val id: Snowflake
    val name: String
    val icon: String? // nullable
    val splash: String? // nullable
    val owner: Boolean? // optional
    val owner_id: Snowflake
    val permissions: PermissionBase? // optional
    val region: String
    val afk_channel_id: Snowflake? // nullable
    val afk_timeout: Int
    val embed_enabled: Boolean? // optional
    val embed_channel_id: Snowflake? // optional
    val verification_level: VerificationLevel
    val default_messages_notifications: DefaultMessageNotificationLevel? // optional?
    val explicit_content_filter: ExplicitContentFilterLevel
    val roles: List<Role>
    val emojis: List<Emoji>
    val features: List<String>
    val mfa_level: MFALevel
    val application_id: Snowflake? // nullable
    val widget_enabled: Boolean? // optional
    val widget_channel_id: Snowflake? // optional
    val system_channel_id: Snowflake? // nullable
    val max_presences: Optional<Int>? // nullable
    val max_members: Int?
    val vanity_url_code: String? // nullable
    val description: String? // nullable
    val banner: String? // nullable
    val premium_tier: PremiumTier
    val premium_subscription_count: Int? // optional
}

@Serializable
data class Guild(
    override val id: Snowflake,
    override val name: String,
    override val icon: String?,
    override val splash: String?,
    override val owner: Boolean? = null,
    override val owner_id: Snowflake,
    override val permissions: PermissionBase? = null,
    override val region: String,
    override val afk_channel_id: Snowflake?,
    override val afk_timeout: Int,
    override val embed_enabled: Boolean? = null,
    override val embed_channel_id: Snowflake? = null,
    override val verification_level: VerificationLevel,
    override val default_messages_notifications: DefaultMessageNotificationLevel? = null,
    override val explicit_content_filter: ExplicitContentFilterLevel,
    override val roles: List<Role>,
    override val emojis: List<Emoji>,
    override val features: List<String>,
    override val mfa_level: MFALevel,
    override val application_id: Snowflake?,
    override val widget_enabled: Boolean? = null,
    override val widget_channel_id: Snowflake? = null,
    override val system_channel_id: Snowflake?,
    override val max_presences: Optional<Int>? = Optional.absent(),
    override val max_members: Int? = null,
    override val vanity_url_code: String?,
    override val description: String?,
    override val banner: String?,
    override val premium_tier: PremiumTier,
    override val premium_subscription_count: Int? = null
) : GuildFace

@Serializable
data class PartialGuild(
    val id: Snowflake? = null,
    val name: String? = null,
    val icon: Optional<String>? = Optional.absent(),
    val splash: Optional<String>? = Optional.absent(),
    val owner: Boolean? = null,
    val owner_id: Snowflake? = null,
    val permissions: PermissionBase? = null,
    val region: String? = null,
    val afk_channel_id: Optional<Snowflake>? = Optional.absent(),
    val afk_timeout: Int? = null,
    val embed_enabled: Boolean? = null,
    val embed_channel_id: Snowflake? = null,
    val verification_level: VerificationLevel? = null,
    val default_messages_notifications: DefaultMessageNotificationLevel? = null,
    val explicit_content_filter: ExplicitContentFilterLevel? = null,
    val roles: List<Role>? = null,
    val emojis: List<Emoji>? = null,
    val features: List<String>? = null,
    val mfa_level: MFALevel? = null,
    val application_id: Optional<Snowflake>? = Optional.absent(),
    val widget_enabled: Boolean? = null,
    val widget_channel_id: Snowflake? = null,
    val system_channel_id: Optional<Snowflake>? = Optional.absent(),
    val max_presences: Optional<Int>? = Optional.absent(),
    val max_members: Int? = null,
    val vanity_url_code: Optional<String>? = Optional.absent(),
    val description: Optional<String>? = Optional.absent(),
    val banner: Optional<String>? = Optional.absent(),
    val premium_tier: PremiumTier? = null,
    val premium_subscription_count: Int? = null
)

interface UnavailableGuildFace {
    val id: Snowflake?
    val name: String?
    val icon: Optional<String>?
    val splash: Optional<String>?
    val owner: Boolean?
    val owner_id: Snowflake?
    val permissions: PermissionBase?
    val region: String?
    val afk_channel_id: Optional<Snowflake>?
    val afk_timeout: Int?
    val embed_enabled: Boolean?
    val embed_channel_id: Snowflake?
    val verification_level: VerificationLevel?
    val default_messages_notifications: DefaultMessageNotificationLevel?
    val explicit_content_filter: ExplicitContentFilterLevel?
    val roles: List<Role>?
    val emojis: List<Emoji>?
    val features: List<String>?
    val mfa_level: MFALevel?
    val application_id: Optional<Snowflake>?
    val widget_enabled: Boolean?
    val widget_channel_id: Snowflake?
    val system_channel_id: Optional<Snowflake>?
    val max_presences: Optional<Int>?
    val max_members: Int?
    val vanity_url_code: Optional<String>?
    val description: Optional<String>?
    val banner: Optional<String>?
    val premium_tier: PremiumTier?
    val premium_subscription_count: Int?
    val unavailable: Boolean?
}

@Serializable
data class UnavailableGuild(
    override val id: Snowflake? = null,
    override val name: String? = null,
    override val icon: Optional<String>? = Optional.absent(),
    override val splash: Optional<String>? = Optional.absent(),
    override val owner: Boolean? = null,
    override val owner_id: Snowflake? = null,
    override val permissions: PermissionBase? = null,
    override val region: String? = null,
    override val afk_channel_id: Optional<Snowflake>? = Optional.absent(),
    override val afk_timeout: Int? = null,
    override val embed_enabled: Boolean? = null,
    override val embed_channel_id: Snowflake? = null,
    override val verification_level: VerificationLevel? = null,
    override val default_messages_notifications: DefaultMessageNotificationLevel? = null,
    override val explicit_content_filter: ExplicitContentFilterLevel? = null,
    override val roles: List<Role>? = null,
    override val emojis: List<Emoji>? = null,
    override val features: List<String>? = null,
    override val mfa_level: MFALevel? = null,
    override val application_id: Optional<Snowflake>? = Optional.absent(),
    override val widget_enabled: Boolean? = null,
    override val widget_channel_id: Snowflake? = null,
    override val system_channel_id: Optional<Snowflake>? = Optional.absent(),
    override val max_presences: Optional<Int>? = Optional.absent(),
    override val max_members: Int? = null,
    override val vanity_url_code: Optional<String>? = Optional.absent(),
    override val description: Optional<String>? = Optional.absent(),
    override val banner: Optional<String>? = Optional.absent(),
    override val premium_tier: PremiumTier? = null,
    override val premium_subscription_count: Int? = null,
    override val unavailable: Boolean? = null
) : UnavailableGuildFace

@Serializable(with = DefaultMessageNotificationLevelSerializer::class)
enum class DefaultMessageNotificationLevel(val value: Int) {
    ALL_MESSAGES(0),
    ONLY_MENTIONS(1)
}

@Serializer(forClass = DefaultMessageNotificationLevel::class)
class DefaultMessageNotificationLevelSerializer : KSerializer<DefaultMessageNotificationLevel> {
    override val descriptor = StringDescriptor.withName("DefaultMessageNotificationLevelSerializer")

    override fun deserialize(decoder: Decoder): DefaultMessageNotificationLevel {
        return when (decoder.decodeInt()) {
            0 -> DefaultMessageNotificationLevel.ALL_MESSAGES
            1 -> DefaultMessageNotificationLevel.ONLY_MENTIONS
            else -> throw IllegalStateException("Illegal value passed for default message notification level.")
        }
    }

    override fun serialize(encoder: Encoder, obj: DefaultMessageNotificationLevel) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable(with = ExplicitContentFilterLevelSerializer::class)
enum class ExplicitContentFilterLevel(val value: Int) {
    DISABLED(0),
    MEMBERS_WITHOUT_ROLES(1),
    ALL_MEMBERS(2)
}

@Serializer(forClass = ExplicitContentFilterLevel::class)
class ExplicitContentFilterLevelSerializer : KSerializer<ExplicitContentFilterLevel> {
    override val descriptor = StringDescriptor.withName("ExplicitContentFilterLevelSerializer")

    override fun deserialize(decoder: Decoder): ExplicitContentFilterLevel {
        return when (decoder.decodeInt()) {
            0 -> ExplicitContentFilterLevel.DISABLED
            1 -> ExplicitContentFilterLevel.MEMBERS_WITHOUT_ROLES
            2 -> ExplicitContentFilterLevel.ALL_MEMBERS
            else -> throw IllegalStateException("Illegal value passed for explicit content filter level.")
        }
    }

    override fun serialize(encoder: Encoder, obj: ExplicitContentFilterLevel) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable(with = MFALevelSerializer::class)
enum class MFALevel(val value: Int) {
    NONE(0),
    ELEVATED(1)
}

@Serializer(forClass = MFALevel::class)
class MFALevelSerializer : KSerializer<MFALevel> {
    override val descriptor = StringDescriptor.withName("MFALevelSerializer")

    override fun deserialize(decoder: Decoder): MFALevel {
        return when (decoder.decodeInt()) {
            0 -> MFALevel.NONE
            1 -> MFALevel.ELEVATED
            else -> throw IllegalStateException("Illegal value passed for MFA level.")
        }
    }

    override fun serialize(encoder: Encoder, obj: MFALevel) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable(with = VerificationLevelSerializer::class)
enum class VerificationLevel(val value: Int) {
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4)
}

@Serializer(forClass = VerificationLevel::class)
class VerificationLevelSerializer : KSerializer<VerificationLevel> {
    override val descriptor = StringDescriptor.withName("VerificationLevelSerializer")

    override fun deserialize(decoder: Decoder): VerificationLevel {
        return when (decoder.decodeInt()) {
            0 -> VerificationLevel.NONE
            1 -> VerificationLevel.LOW
            2 -> VerificationLevel.MEDIUM
            3 -> VerificationLevel.HIGH
            4 -> VerificationLevel.VERY_HIGH
            else -> throw IllegalStateException("Illegal value passed for verification level.")
        }
    }

    override fun serialize(encoder: Encoder, obj: VerificationLevel) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable(with = PremiumTierSerializer::class)
enum class PremiumTier(val value: Int) {
    NONE(0),
    TIER_1(1),
    TIER_2(2),
    TIER_3(3)
}

@Serializer(forClass = PremiumTier::class)
class PremiumTierSerializer : KSerializer<PremiumTier> {
    override val descriptor = StringDescriptor.withName("PremiumTierSerializer")

    override fun deserialize(decoder: Decoder): PremiumTier {
        return when (decoder.decodeInt()) {
            0 -> PremiumTier.NONE
            1 -> PremiumTier.TIER_1
            2 -> PremiumTier.TIER_2
            3 -> PremiumTier.TIER_3
            else -> throw IllegalStateException("Illegal value passed for premium tier.")
        }
    }

    override fun serialize(encoder: Encoder, obj: PremiumTier) {
        encoder.encodeInt(obj.value)
    }
}