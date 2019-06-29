package com.github.princesslana.smalldkt.type.invite

import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.http.delete
import com.github.princesslana.smalldkt.http.get
import com.github.princesslana.smalldkt.http.queryString

suspend fun SmallDData<*>.getInvite(code: String, with_counts: Boolean? = null) =
    smallD.get(
        "/invites/$code${queryString { if (with_counts != null) +("with_counts" to with_counts) }}",
        Invite.serializer()
    )

suspend fun SmallDData<*>.deleteInvite(code: String) = smallD.delete("/invites/$code", Invite.serializer())