package com.ria4.odoo.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by glovebx on 11.11.2019.
 */
open class Common3RPCRequest(@SerializedName("method") @Expose val method: String
                             , @Transient val db: String
                             , @Transient val uid: String
                             , @Transient val password: String): BaseRPCRequest() {

    @SerializedName("service")
    @Expose
    val service: String = "common"

    init {
        super.args[0] = db
        super.args[1] = uid
        super.args[2] = password
    }
}

