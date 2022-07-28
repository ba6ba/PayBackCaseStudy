package com.ba6ba.network

import com.google.gson.*

object GsonHelper {

    val prettyGson: Gson by lazy {
        GsonBuilder().setPrettyPrinting().create()
    }

    val gsonIdentity: Gson by lazy {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setPrettyPrinting()
            .setExclusionStrategies(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes): Boolean {
                    return false
                }

                override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                    return false
                }
            }).create()
    }
}
