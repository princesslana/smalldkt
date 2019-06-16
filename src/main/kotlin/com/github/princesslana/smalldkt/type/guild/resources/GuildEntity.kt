package com.github.princesslana.smalldkt.type.guild.resources

import com.github.princesslana.smalldkt.type.Identifiable
import com.github.princesslana.smalldkt.type.Optional
import com.github.princesslana.smalldkt.type.Snowflake
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor


interface PartialGuild: Identifiable {
    val name: String
    val icon: String?
    val splash: String?
    val features: List<String>
    val verification_level: VerificationLevel
    val banner: String?
    val vanity_url_code: String?
    val description: String?
    val owner: Optional<Boolean>
    val permissions: Optional<Int>
    val embed_enabled: Optional<Boolean>
    val embed_channel_id: Optional<Snowflake>
    val widget_enabled: Optional<Boolean>
    val widget_channel_id: Optional<Snowflake>
    val premium_subscription_count: Optional<Int>
}

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
            else -> throw IllegalStateException("Illegal Target user type.")
        }
    }

    override fun serialize(encoder: Encoder, obj: VerificationLevel) {
        encoder.encodeInt(obj.level)
    }
}

@Serializable
data class PartialGuildImpl(
        override val name: String,
        override val icon: String? = null,
        override val splash: String? = null,
        override val features: List<String>,
        override val owner: Optional<Boolean> = Optional.absent(),
        override val verification_level: VerificationLevel,
        override val banner: String? = null,
        override val vanity_url_code: String? = null,
        override val description: String? = null,
        override val id: Snowflake,
        override val permissions: Optional<Int> = Optional.absent(),
        override val embed_enabled: Optional<Boolean> = Optional.absent(),
        override val embed_channel_id: Optional<Snowflake> = Optional.absent(),
        override val widget_enabled: Optional<Boolean> = Optional.absent(),
        override val widget_channel_id: Optional<Snowflake> = Optional.absent(),
        override val premium_subscription_count: Optional<Int> = Optional.absent()
): PartialGuild