package com.ria4.odoo.domain.repository

import com.ria4.odoo.data.request.CommonRPCRequest
import com.ria4.odoo.data.request.DbRPCRequest
import com.ria4.odoo.domain.entity.ServerVersion
import io.reactivex.Flowable

/**
 * Created by glovebx on 11.11.2019.
 */
interface CommonRepository {

    fun getServerVersion(request: CommonRPCRequest): Flowable<ServerVersion>

    fun listDb(request: DbRPCRequest): Flowable<Array<String>>

}