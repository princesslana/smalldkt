package com.github.princesslana.smalldkt

interface Request {
    val path: String

    fun executeRequest(smallDData: SmallDData)
}