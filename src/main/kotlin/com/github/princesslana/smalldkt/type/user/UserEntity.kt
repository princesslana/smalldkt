package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import com.github.princesslana.smalldkt.type.guild.PartialGuildMember
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

interface UserFace {
    val id: Snowflake
    val username: String
    val discriminator: String
    val avatar: String? // nullable
    val bot: Boolean? // optional
    val mfa_enabled: Boolean? // optional
    val locale: String? // optional
    val verified: Boolean? // optional
    val email: String? // optional
    val flags: Long? // optional
    val premium_type: PremiumType? // optional
}

@Serializable
data class User(
    override val id: Snowflake,
    override val username: String,
    override val discriminator: String,
    override val avatar: String?,
    override val bot: Boolean? = null,
    override val mfa_enabled: Boolean? = null,
    override val locale: String? = null,
    override val verified: Boolean? = null,
    override val email: String? = null,
    override val flags: Long? = null,
    override val premium_type: PremiumType? = null
) : UserFace

@Serializable
data class PartialGuildUser(
    override val id: Snowflake,
    override val username: String,
    override val discriminator: String,
    override val avatar: String?,
    override val bot: Boolean? = null,
    override val mfa_enabled: Boolean? = null,
    override val locale: String? = null,
    override val verified: Boolean? = null,
    override val email: String? = null,
    override val flags: Long? = null,
    override val premium_type: PremiumType? = null,
    val member: PartialGuildMember
) : UserFace

@Serializable
data class PartialUser(
    val id: Snowflake? = null,
    val username: String? = null,
    val discriminator: String? = null,
    val avatar: Optional<String>? = Optional.absent(),
    val bot: Boolean? = null,
    val mfa_enabled: Boolean? = null,
    val locale: String? = null,
    val verified: Boolean? = null,
    val email: String? = null,
    val flags: Long? = null,
    val premium_type: PremiumType? = null
)

enum class UserFlag(shift: Int, private val value: Long = 1L shl shift) {
    NONE(0, 0),
    DISCORD_EMPLOYEE(0),
    DISCORD_PARTNER(1),
    HYPE_SQUAD_EVENTS(2),
    BUG_HUNTER(3),
    HOUSE_BRAVERY(6),
    HOUSE_BRILLIANCE(7),
    HOUSE_BALANCE(8),
    EARLY_SUPPORTER(9),
    TEAM_USER(10);

    fun hasFlag(flags: Long, flag: UserFlag): Boolean {
        return (flags and flag.value) == flag.value
    }

    fun compileFlags(vararg flags: UserFlag): Long {
        return flags.fold(0L, { a, f -> a or f.value })
    }
}

@Serializable(with = PremiumTypeSerializer::class)
enum class PremiumType(val value: Int) {
    NITRO_CLASSIC(1),
    NITRO(2)
}

@Serializer(forClass = PremiumType::class)
class PremiumTypeSerializer : KSerializer<PremiumType> {
    override val descriptor = StringDescriptor.withName("PremiumTypeSerializer")

    override fun deserialize(decoder: Decoder): PremiumType {
        return when (decoder.decodeInt()) {
            1 -> PremiumType.NITRO_CLASSIC
            2 -> PremiumType.NITRO
            else -> throw IllegalStateException("Illegal value passed for premium type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: PremiumType) {
        encoder.encodeInt(obj.value)
    }
}