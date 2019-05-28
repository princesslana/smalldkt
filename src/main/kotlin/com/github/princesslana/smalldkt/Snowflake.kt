package com.github.princesslana.smalldkt

import kotlinx.serialization.Serializable

@Serializable(with = WrapperSerializer::class)
class Snowflake(id: Long) : Wrapper<Long>(id)