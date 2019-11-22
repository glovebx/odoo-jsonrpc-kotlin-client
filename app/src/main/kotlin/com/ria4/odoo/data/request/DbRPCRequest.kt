package com.ria4.odoo.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * 允许的方法
 * 'db_exist', 'list', 'list_lang', 'server_version'.
 */
open class DbRPCRequest(@SerializedName("method") @Expose val method: String): BaseRPCRequest() {

    @SerializedName("service")
    @Expose
    val service: String = "db"
}

