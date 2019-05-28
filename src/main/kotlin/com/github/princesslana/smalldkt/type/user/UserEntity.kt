package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.Snowflake
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.lang.IllegalStateException

@Serializable
data class User(
    val id: Snowflake,
    val username: String,
    val discriminator: String,
    val avatar: String?,
    val bot: String? = null,
    @SerialName("mfa_enabled") val mfaEnabled: Boolean? = null,
    val locale: String? = null,
    val verified: String? = null,
    val email: String? = null,
    val flags: Flag? = null,
    @SerialName("premium_type") val premiumType: PremiumType? = null
)

enum class Flag(shift: Int, val value: Int = 1 shl shift) {
    NONE(0),
    DISCORD_EMPLOYEE(0),
    DISCORD_PARTNER(1),
    HYPESQUAD_EVENTS(2),
    BUG_HUNTER(3),
    HOUSE_BRAVERY(6),
    HOUSE_BRILLIANCE(7),
    HOUSE_BALANCE(8),
    EARLY_SUPPORTER(9);

    companion object : KSerializer<Flag> {
        private val typeMap: MutableMap<Int, Flag> = mutableMapOf()

        init {
            values().forEach { typeMap[it.value] = it }
        }

        override val descriptor: SerialDescriptor = StringDescriptor.withName("FlagsSerializer")

        override fun deserialize(decoder: Decoder): Flag {
            return typeMap[decoder.decodeInt()]!!
        }

        override fun serialize(encoder: Encoder, obj: Flag) {
            encoder.encodeInt(obj.value)
        }

    }
}

enum class PremiumType(val value: Int) {
    NITRO_CLASSIC(1),
    NITRO(2);

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