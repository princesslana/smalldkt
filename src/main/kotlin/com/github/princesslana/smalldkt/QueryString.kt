package com.github.princesslana.smalldkt

data class QueryString(val parts: List<Pair<String, String>>) {
    fun build(): String {
        if (parts.isEmpty()) return ""
        return "?${parts.joinToString("&") {
            "${it.first}=${it.second}"
        }}"
    }
}

class QueryStringBuilder(val list: MutableList<Pair<String, String>>) {
    operator fun Pair<String, String>.unaryPlus() {
        list.add(this)
    }
}

fun queryString(block: QueryStringBuilder.() -> Unit): String =
    QueryString(QueryStringBuilder(mutableListOf()).also(block).list).build()