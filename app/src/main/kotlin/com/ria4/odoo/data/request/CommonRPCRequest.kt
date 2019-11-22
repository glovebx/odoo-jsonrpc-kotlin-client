package com.ria4.odoo.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by glovebx on 11.11.2019.
 */
open class CommonRPCRequest(@SerializedName("method") @Expose val method: String): BaseRPCRequest() {

    @SerializedName("service")
    @Expose
    val service: String = "common"
}

