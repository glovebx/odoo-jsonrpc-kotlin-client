package com.ria4.odoo.data.network

import com.ria4.odoo.data.pref.Preferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by glovebx on 11.11.2019.
 */
class JsonHeaderInterceptor constructor(private val preferences: Preferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val contentType = chain.request().header("Content-Type")
        if (contentType.isNullOrEmpty()) {
            val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
            return chain.proceed(request)
        }

        return chain.proceed(chain.request())
    }
}