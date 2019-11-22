package com.ria4.odoo.data.network

import com.ria4.odoo.data.request.CommonRPCRequest
import com.ria4.odoo.data.request.DbRPCRequest
import com.ria4.odoo.data.response.ServerVersion
import com.segment.jsonrpc.JsonRPC
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by glovebx on 11.11.2019.
 */
interface CommonApiService {

    @JsonRPC("call") @POST("jsonrpc")
//    @Headers("Content-Type: application/json")
    fun getServerVersion(@Body request: CommonRPCRequest): Flowable<ServerVersion>

    @JsonRPC("call") @POST("jsonrpc")
//    @Headers("Content-Type: application/json")
    fun listDb(@Body request: DbRPCRequest): Flowable<Array<String>>

}