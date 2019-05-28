package com.github.princesslana.smalldkt.type.user

import com.github.princesslana.smalldkt.Wrapper
import com.github.princesslana.smalldkt.WrapperSerializer
import kotlinx.serialization.Serializable

@Serializable(with = WrapperSerializer::class)
class AvatarData(string: String) : Wrapper<String>(string)