package com.github.princesslana.smalldkt.type.voice

import com.github.princesslana.smalldkt.SmallDData
import com.github.princesslana.smalldkt.http.get
import kotlinx.serialization.internal.ArrayListSerializer

suspend fun SmallDData<*>.listVoiceRegions() =
    smallD.get("/voice/regions", ArrayListSerializer(VoiceRegion.serializer()))