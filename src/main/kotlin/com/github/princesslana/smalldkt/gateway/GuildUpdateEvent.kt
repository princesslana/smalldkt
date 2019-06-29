package com.github.princesslana.smalldkt.gateway

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.permissions.PermissionBase
import com.github.princesslana.smalldkt.permissions.Role
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.emoji.Emoji
import com.github.princesslana.smalldkt.type.guild.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class GuildUpdateEvent(
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
) : GuildFace, GuildEvent {
    override val guild_id: Snowflake = id
}