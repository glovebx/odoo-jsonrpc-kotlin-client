package com.ria4.odoo.data.repository

import com.google.gson.Gson
import com.ria4.odoo.data.cache.MemoryCache
import com.ria4.odoo.data.mapper.Mapper
import com.ria4.odoo.data.network.CommonApiService
import com.ria4.odoo.data.request.CommonRPCRequest
import com.ria4.odoo.data.request.DbRPCRequest
import com.ria4.odoo.domain.entity.ServerVersion
import com.ria4.odoo.domain.repository.CommonRepository
import io.reactivex.*

/**
 * Created by glovebx on 11.11.2019.
 */
class CommonDataRepository(
        private val commonApiService: CommonApiService,
        private var gson: Gson,
        private val memoryCache: MemoryCache,
        private val mapper: Mapper) : CommonRepository {

    override fun getServerVersion(request: CommonRPCRequest): Flowable<ServerVersion>
            = commonApiService.getServerVersion(request).map {
        mapper.translate(it)
    }

    override fun listDb(request: DbRPCRequest): Flowable<Array<String>> = commonApiService.listDb(request)

}