package com.github.princesslana.smalldkt.type.auditlog

import com.github.princesslana.smalldkt.permissions.Role
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.channel.Overwrite
import com.github.princesslana.smalldkt.type.user.User
import com.github.princesslana.smalldkt.type.webhook.resources.Webhook
import kotlinx.serialization.*
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class AuditLog(
    val webhooks: List<Webhook>,
    val users: List<User>,
    val audit_log_entries: List<AuditLogEntry>
)

@Serializable
data class AuditLogEntry(
    val target_id: String?,
    val changes: List<AuditLogChange>? = null,
    val user_id: Snowflake,
    val id: Snowflake,
    val action_type: AuditLogEvent,
    val options: OptionalAuditEntryInfo? = null,
    val reason: String? = null
)

@Serializable(AuditLogEventSerializer::class)
enum class AuditLogEvent(val value: Int) {
    GUILD_UPDATE(1),
    CHANNEL_CREATE(10),
    CHANNEL_UPDATE(11),
    CHANNEL_DELETE(12),
    CHANNEL_OVERWRITE_CREATE(13),
    CHANNEL_OVERWRITE_UPDATE(14),
    CHANNEL_OVERWRITE_DELETE(15),
    MEMBER_KICK(20),
    MEMBER_PRUNE(21),
    MEMBER_BAN_ADD(22),
    MEMBER_BAN_REMOVE(23),
    MEMBER_UPDATE(24),
    MEMBER_ROLE_UPDATE(25),
    ROLE_CREATE(30),
    ROLE_UPDATE(31),
    ROLE_DELETE(32),
    INVITE_CREATE(40),
    INVITE_UPDATE(41),
    INVITE_DELETE(42),
    WEBHOOK_CREATE(50),
    WEBHOOK_UPDATE(51),
    WEBHOOK_DELETE(52),
    EMOJI_CREATE(60),
    EMOJI_UPDATE(61),
    EMOJI_DELETE(62),
    MESSAGE_DELETE(72)
}

@Serializer(AuditLogEvent::class)
class AuditLogEventSerializer : KSerializer<AuditLogEvent> {
    override val descriptor = StringDescriptor.withName("AuditLogEventSerializer")

    override fun deserialize(decoder: Decoder): AuditLogEvent {
        return when (decoder.decodeInt()) {
            1 -> AuditLogEvent.GUILD_UPDATE
            10 -> AuditLogEvent.CHANNEL_CREATE
            11 -> AuditLogEvent.CHANNEL_UPDATE
            12 -> AuditLogEvent.CHANNEL_DELETE
            13 -> AuditLogEvent.CHANNEL_OVERWRITE_CREATE
            14 -> AuditLogEvent.CHANNEL_OVERWRITE_UPDATE
            15 -> AuditLogEvent.CHANNEL_OVERWRITE_DELETE
            20 -> AuditLogEvent.MEMBER_KICK
            21 -> AuditLogEvent.MEMBER_PRUNE
            22 -> AuditLogEvent.MEMBER_BAN_ADD
            23 -> AuditLogEvent.MEMBER_BAN_REMOVE
            24 -> AuditLogEvent.MEMBER_UPDATE
            25 -> AuditLogEvent.MEMBER_ROLE_UPDATE
            30 -> AuditLogEvent.ROLE_CREATE
            31 -> AuditLogEvent.ROLE_UPDATE
            32 -> AuditLogEvent.ROLE_DELETE
            40 -> AuditLogEvent.INVITE_CREATE
            41 -> AuditLogEvent.INVITE_UPDATE
            42 -> AuditLogEvent.INVITE_DELETE
            50 -> AuditLogEvent.WEBHOOK_CREATE
            51 -> AuditLogEvent.WEBHOOK_UPDATE
            52 -> AuditLogEvent.WEBHOOK_DELETE
            60 -> AuditLogEvent.EMOJI_CREATE
            61 -> AuditLogEvent.EMOJI_UPDATE
            62 -> AuditLogEvent.EMOJI_DELETE
            72 -> AuditLogEvent.MESSAGE_DELETE
            else -> throw IllegalStateException("Illegal value passed for audit log event.")
        }
    }

    override fun serialize(encoder: Encoder, obj: AuditLogEvent) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable
data class OptionalAuditEntryInfo(
    val delete_member_days: String? = null,
    val members_removed: String? = null,
    val channel_id: Snowflake? = null,
    val count: String? = null,
    val id: Snowflake? = null,
    val type: String? = null,
    val role_name: String? = null
)

// do not touch unless it errors

@Serializable(AuditLogChangeSerializer::class)
data class AuditLogChange(
    val new_value: AuditLogChangeType<*>? = null,
    val old_value: AuditLogChangeType<*>? = null,
    val key: String
)

@Serializer(AuditLogChange::class)
class AuditLogChangeSerializer : KSerializer<AuditLogChange> {
    override val descriptor = object : SerialClassDescImpl("AuditLogChange") {
        init {
            addElement("key")
            addElement("new_value")
            addElement("old_value")
        }
    }

    override fun deserialize(decoder: Decoder): AuditLogChange {
        lateinit var key: String
        val struct = decoder.beginStructure(descriptor)
        var serializer: ((Int) -> AuditLogChangeType<*>)? = null
        var new_value: AuditLogChangeType<*>? = null
        var old_value: AuditLogChangeType<*>? = null

        loop@ while (true) {
            when (val i = struct.decodeElementIndex(descriptor)) {
                CompositeDecoder.READ_DONE -> break@loop
                0 -> {
                    key = struct.decodeStringElement(descriptor, i)
                    serializer = when (key) {
                        "name", "icon_hash", "splash_hash",
                        "region", "vanity_url_code", "topic",
                        "code", "nick", "avatar_hash",
                        "type"
                        -> { z ->
                            AuditLogChangeString(
                                struct.decodeStringElement(
                                    descriptor,
                                    z
                                )
                            )
                        }
                        "owner_id", "afk_channel_id", "widget_channel_id",
                        "application_id", "channel_id", "inviter_id",
                        "id"
                        -> { z ->
                            AuditLogChangeSnowflake(
                                struct.decodeSerializableElement(
                                    descriptor,
                                    z,
                                    Snowflake.serializer()
                                )
                            )
                        }
                        "afk_timeout", "mfa_level", "verification_level",
                        "explicit_content_filter", "default_message_notifications", "prune_delete_days",
                        "position", "bitrate", "permissions",
                        "color", "allow", "deny",
                        "max_uses", "uses", "max_age"
                        -> { z ->
                            AuditLogChangeInt(
                                struct.decodeIntElement(
                                    descriptor,
                                    z
                                )
                            )
                        }
                        "\$add", "\$remove"
                        -> { z ->
                            AuditLogChangeRoleList(
                                struct.decodeSerializableElement(
                                    descriptor,
                                    z,
                                    ArrayListSerializer(Role.serializer())
                                )
                            )
                        }
                        "widget_enabled", "nsfw", "hoist",
                        "mentionable", "temporary", "deaf",
                        "mute"
                        -> { z ->
                            AuditLogChangeBool(
                                struct.decodeBooleanElement(
                                    descriptor,
                                    z
                                )
                            )
                        }
                        "permission_overwrites"
                        -> { z ->
                            AuditLogChangeOverwriteList(
                                struct.decodeSerializableElement(
                                    descriptor,
                                    z,
                                    ArrayListSerializer(Overwrite.serializer())
                                )
                            )
                        }
                        else -> throw IllegalStateException("Unknown audit log change key $key")
                    }
                }
                1 -> new_value = serializer?.invoke(i)
                2 -> old_value = serializer?.invoke(i)
                else -> throw SerializationException("Unknown index $i")
            }
        }
        struct.endStructure(descriptor)
        return AuditLogChange(new_value, old_value, key)
    }

    override fun serialize(encoder: Encoder, obj: AuditLogChange) {
        val struct = encoder.beginStructure(descriptor)
        struct.encodeStringElement(descriptor, 0, obj.key)
        obj.new_value?.encode(struct, descriptor, 1)
        obj.old_value?.encode(struct, descriptor, 2)
        struct.endStructure(descriptor)
    }
}

sealed class AuditLogChangeType<T>(internal val serializer: KSerializer<T>, val value: T) {
    internal fun encode(encoder: CompositeEncoder, descriptor: SerialDescriptor, index: Int) {
        encoder.encodeSerializableElement(descriptor, index, serializer, value)
    }
}

class AuditLogChangeBool(boolean: Boolean) : AuditLogChangeType<Boolean>(Boolean.serializer(), boolean)
class AuditLogChangeInt(int: Int) : AuditLogChangeType<Int>(Int.serializer(), int)
class AuditLogChangeString(string: String) : AuditLogChangeType<String>(String.serializer(), string)
class AuditLogChangeSnowflake(snowflake: Snowflake) : AuditLogChangeType<Snowflake>(Snowflake.serializer(), snowflake)
class AuditLogChangeRoleList(roles: List<Role>) :
    AuditLogChangeType<List<Role>>(ArrayListSerializer(Role.serializer()), roles)

class AuditLogChangeOverwriteList(overwrites: List<Overwrite>) :
    AuditLogChangeType<List<Overwrite>>(ArrayListSerializer(Overwrite.serializer()), overwrites)