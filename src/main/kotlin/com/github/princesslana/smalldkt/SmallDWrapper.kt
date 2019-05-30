package com.github.princesslana.smalldkt

import com.github.princesslana.smalld.SmallD

data class SmallDData<T>(internal val smallD: SmallD, val event: T) where T : Any, T : Event