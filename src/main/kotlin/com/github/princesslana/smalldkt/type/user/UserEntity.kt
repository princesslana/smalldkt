package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.Optional
import com.github.princesslana.smalldkt.Snowflake
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable
data class User(
    val id: Snowflake,
    val username: String,
    val discriminator: String,
    val avatar: String?,
    val bot: Boolean? = null,
    @SerialName("mfa_enabled") val mfaEnabled: Boolean? = null,
    val locale: String? = null,
    val verified: String? = null,
    val email: String? = null,
    val flags: Flag? = null,
    @SerialName("premium_type") val premiumType: PremiumType? = null
) {
    fun isBot() = bot == true
}

@Serializable
data class PresenceUser(
    val id: Snowflake,
    val username: String? = null,
    val discriminator: String? = null,
    val avatar: Optional<String>? = Optional.absent(),
    val bot: Boolean? = null,
    @SerialName("mfa_enabled") val mfaEnabled: Boolean? = null,
    val locale: String? = null,
    val verified: String? = null,
    val email: String? = null,
    val flags: Flag? = null,
    @SerialName("premium_type") val premiumType: PremiumType? = null
) {
    fun isBot() = bot == true
}

@Serializable
class AvatarData(val string: String) {
    @Serializer(forClass = AvatarData::class)
    companion object : KSerializer<AvatarData> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("AvatarDataSerializer")

        override fun deserialize(decoder: Decoder): AvatarData {
            return AvatarData(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, obj: AvatarData) {
            encoder.encodeString(obj.string)
        }
    }
}

@Serializable
enum class Flag(shift: Int, val value: Int = 1 shl shift) {
    NONE(0, 0),
    DISCORD_EMPLOYEE(0),
    DISCORD_PARTNER(1),
    HYPESQUAD_EVENTS(2),
    BUG_HUNTER(3),
    HOUSE_BRAVERY(6),
    HOUSE_BRILLIANCE(7),
    HOUSE_BALANCE(8),
    EARLY_SUPPORTER(9);

    @Serializer(forClass = Flag::class)
    companion object : KSerializer<Flag> {
        private val typeMap: MutableMap<Int, Flag> = mutableMapOf()

        init {
            values().forEach { typeMap[it.value] = it }
        }

        override val descriptor: SerialDescriptor = StringDescriptor.withName("FlagsSerializer")

        override fun deserialize(decoder: Decoder): Flag {
            return when (decoder.decodeInt()) {
                0 -> NONE
                1 shl 0 -> DISCORD_EMPLOYEE
                1 shl 1 -> DISCORD_PARTNER
                1 shl 2 -> HYPESQUAD_EVENTS
                1 shl 3 -> BUG_HUNTER
                1 shl 6 -> HOUSE_BRAVERY
                1 shl 7 -> HOUSE_BRILLIANCE
                1 shl 8 -> HOUSE_BALANCE
                1 shl 9 -> EARLY_SUPPORTER
                else -> throw IllegalStateException("Illegal Flag Type.")
            }
        }

        override fun serialize(encoder: Encoder, obj: Flag) {
            encoder.encodeInt(obj.value)
        }

    }
}

@Serializable
enum class PremiumType(val value: Int) {
    NITRO_CLASSIC(1),
    NITRO(2);

    @Serializer(forClass = PremiumType::class)
    companion object : KSerializer<PremiumType> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("PremiumTypeSerializer")

        override fun deserialize(decoder: Decoder): PremiumType {
            return when (decoder.decodeInt()) {
                1 -> NITRO_CLASSIC
                2 -> NITRO
                else -> throw IllegalStateException("Illegal premium type.")
            }
        }

        override fun serialize(encoder: Encoder, obj: PremiumType) {
            encoder.encodeInt(obj.value)
        }

    }
}