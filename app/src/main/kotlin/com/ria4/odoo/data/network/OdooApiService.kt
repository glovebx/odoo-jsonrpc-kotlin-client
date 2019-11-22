package com.ria4.odoo.data.network

import com.ria4.odoo.data.request.CommonRPCRequest
import com.ria4.odoo.data.request.DbRPCRequest
import com.ria4.odoo.data.request.ModelRPCRequest
import com.ria4.odoo.domain.entity.ServerVersion
import com.segment.jsonrpc.JsonRPC
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by glovebx on 11.11.2019.
 */
interface OdooApiService {

    @JsonRPC("call")
    @POST("jsonrpc")
    @Headers("Content-Type: application/json")
    fun common(@Body request: CommonRPCRequest): Flowable<ServerVersion>

    @JsonRPC("call")
    @POST("jsonrpc")
    @Headers("Content-Type: application/json")
    fun db(@Body request: DbRPCRequest): Flowable<ServerVersion>

    @JsonRPC("call")
    @POST("jsonrpc")
    @Headers("Content-Type: application/json")
    fun model(@Body request: ModelRPCRequest): Flowable<ServerVersion>

}