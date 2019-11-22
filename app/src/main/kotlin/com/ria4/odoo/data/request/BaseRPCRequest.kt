package com.ria4.odoo.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by glovebx on 11.11.2019.
 */
open class BaseRPCRequest {

    @SerializedName("args")
    @Expose
    var args: MutableList<Any> = mutableListOf()

    @SerializedName("context")
    @Expose
    var context: MutableMap<String, Any> = mutableMapOf()
}

