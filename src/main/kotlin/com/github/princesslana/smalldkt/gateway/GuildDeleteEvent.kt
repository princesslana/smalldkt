package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.Event
import com.github.princesslana.smalldkt.permissions.PermissionBase
import com.github.princesslana.smalldkt.permissions.Role
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.emoji.Emoji
import com.github.princesslana.smalldkt.type.guild.*
import kotlinx.serialization.Serializable

@Serializable
data class GuildDeleteEvent(
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
) : UnavailableGuildFace, Event