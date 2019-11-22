package com.ria4.odoo.data.cache

import android.util.LruCache
import com.ria4.odoo.domain.fetcher.result_listener.RequestType
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by glovebx on 11.11.2019.
 */
@Singleton
class MemoryCache @Inject constructor() : LruCache<RequestType, Any>(1024 * 1024 * 2)/* 2 MB */ {

    inline infix fun <reified V> getCacheForType(key: RequestType) = get(key) as V

    infix fun hasCacheFor(requestType: RequestType) = getCacheForType<Any?>(requestType) != null

    infix fun clearCacheFor(requestType: RequestType) {
        remove(requestType)
    }

    fun clearCache() {
        evictAll()
    }
}