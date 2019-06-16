package com.github.princesslana.smalldkt.type.user.user

import com.github.princesslana.smalldkt.type.Identifiable
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

interface PartialUser: Identifiable{
    val username: String?
    val discriminator: String?
    val avatar: Optional<String>?
    val bot: Boolean?
    val mfa_enabled: Boolean?
    val locale: String?
    val verified: Boolean?
    val email: String?
    val flags: Int?
    val premium_type: PremiumType?
}

@Serializable
data class PartialUserImpl(
        override val id: Snowflake,
        override val username: String? = null,
        override val discriminator: String? = null,
        override val avatar: Optional<String>? = Optional.absent(),
        override val bot: Boolean? = null,
        override val mfa_enabled: Boolean? = null,
        override val locale: String? = null,
        override val verified: Boolean? = null,
        override val email: String? = null,
        override val flags: Int? = null,
        override val premium_type: PremiumType? = null
): PartialUser

@Serializable(with = UserImpl.`$serializer`::class)
interface User : Identifiable {
    val username: String
    val discriminator: String
    val avatar: String?
    val bot: Boolean?
    val mfa_enabled: Boolean?
    val locale: String?
    val verified: Boolean?
    val email: String?
    val flags: Flag?
    val premium_type: PremiumType?

    fun isBot() = bot == true
}

@Serializable
data class UserImpl(
    override val username: String,
    override val discriminator: String,
    override val avatar: String?,
    override val bot: Boolean? = null,
    override val mfa_enabled: Boolean? = null,
    override val locale: String? = null,
    override val verified: Boolean? = null,
    override val email: String? = null,
    override val flags: Flag? = null,
    override val premium_type: PremiumType? = null,
    override val id: Snowflake
) : User

@Serializable(with = FlagSerializer::class)
enum class Flag(shift: Int, val value: Int = 1 shl shift) {
    NONE(0, 0),
    DISCORD_EMPLOYEE(0),
    DISCORD_PARTNER(1),
    HYPESQUAD_EVENTS(2),
    BUG_HUNTER(3),
    HOUSE_BRAVERY(6),
    HOUSE_BRILLIANCE(7),
    HOUSE_BALANCE(8),
    EARLY_SUPPORTER(9)
}

@Serializer(forClass = Flag::class)
class FlagSerializer : KSerializer<Flag> {
    private val typeMap: MutableMap<Int, Flag> = mutableMapOf()

    init {
        Flag.values().forEach { typeMap[it.value] = it }
    }

    override val descriptor: SerialDescriptor = StringDescriptor.withName("FlagsSerializer")

    override fun deserialize(decoder: Decoder): Flag {
        return when (decoder.decodeInt()) {
            0 -> Flag.NONE
            1 shl 0 -> Flag.DISCORD_EMPLOYEE
            1 shl 1 -> Flag.DISCORD_PARTNER
            1 shl 2 -> Flag.HYPESQUAD_EVENTS
            1 shl 3 -> Flag.BUG_HUNTER
            1 shl 6 -> Flag.HOUSE_BRAVERY
            1 shl 7 -> Flag.HOUSE_BRILLIANCE
            1 shl 8 -> Flag.HOUSE_BALANCE
            1 shl 9 -> Flag.EARLY_SUPPORTER
            else -> throw IllegalStateException("Illegal Flag Type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: Flag) {
        encoder.encodeInt(obj.value)
    }
}

@Serializable(with = PremiumTypeSerializer::class)
enum class PremiumType(val value: Int) {
    NITRO_CLASSIC(1),
    NITRO(2)
}

@Serializer(forClass = PremiumType::class)
class PremiumTypeSerializer : KSerializer<PremiumType> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("PremiumTypeSerializer")

    override fun deserialize(decoder: Decoder): PremiumType {
        return when (decoder.decodeInt()) {
            1 -> PremiumType.NITRO_CLASSIC
            2 -> PremiumType.NITRO
            else -> throw IllegalStateException("Illegal premium type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: PremiumType) {
        encoder.encodeInt(obj.value)
    }
}