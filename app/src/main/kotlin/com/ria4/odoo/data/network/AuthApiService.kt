package com.ria4.odoo.data.network

import com.ria4.odoo.data.request.CommonRPCRequest
import com.segment.jsonrpc.JsonRPC
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by glovebx on 11.11.2019.
 */
interface AuthApiService {

    @JsonRPC("call") @POST("jsonrpc")
//    @Headers("Content-Type: application/json")
    fun authenticate(@Body request: CommonRPCRequest): Flowable<Int>

}