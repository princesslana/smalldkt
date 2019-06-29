package com.github.princesslana.smalldkt.http

data class QueryString(val parts: List<Pair<String, String>>) {
    fun build(): String {
        if (parts.isEmpty()) return ""
        return "?${parts.joinToString("&") {
            "${it.first}=${it.second}"
        }}"
    }
}

class QueryStringBuilder(val list: MutableList<Pair<String, String>>) {
    operator fun Pair<String, Any>.unaryPlus() {
        list.add(this.first to this.second.toString())
    }
}

fun queryString(block: QueryStringBuilder.() -> Unit): String =
    QueryString(
        QueryStringBuilder(
            mutableListOf()
        ).also(block).list
    ).build()