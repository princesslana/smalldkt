package com.github.princesslana.smalldkt.type.invite

import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.http.queryString
import com.github.princesslana.smalldkt.http.get
import com.github.princesslana.smalldkt.http.delete
import com.github.princesslana.smalldkt.type.invite.resources.InviteImpl

suspend fun SmallDData<*>.getInvite(code: String,includeCount: Boolean) =
        smallD.get("/invites/$code" + queryString{
            +("with_counts" to "$includeCount")
        },
                InviteImpl.serializer()
        )

suspend fun SmallDData<*>.deleteInvite(code: String) =
        smallD.delete(
                "/invites/$code",
                InviteImpl.serializer()
        )
