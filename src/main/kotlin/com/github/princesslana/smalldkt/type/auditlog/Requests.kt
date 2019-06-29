package com.github.princesslana.smalldkt.type.auditlog

import com.github.princesslana.smalldkt.GuildEvent
import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.http.get
import com.github.princesslana.smalldkt.http.queryString
import com.github.princesslana.smalldkt.type.Snowflake

suspend fun SmallDData<*>.getGuildAuditLog(guildId: Snowflake, payload: GetAuditLogPayload): AuditLog =
    smallD.get("/guilds/$guildId/audit-logs" + payload.queryString, AuditLog.serializer())

suspend fun SmallDData<out GuildEvent>.getGuildAuditLog(payload: GetAuditLogPayload) =
    getGuildAuditLog(event.guild_id, payload)

class GetAuditLogPayload(
    userId: Snowflake? = null,
    actionType: AuditLogEvent? = null,
    before: Snowflake? = null,
    limit: Int? = null
) {
    init {
        require(limit == null || limit in 1..100)
    }

    val queryString = queryString {
        if (userId != null) +("user_id" to userId)
        if (actionType != null) +("action_type" to actionType.value)
        if (before != null) +("before" to before)
        if (limit != null) +("limit" to limit)
    }
}