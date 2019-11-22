package com.ria4.odoo.data.response

import com.google.gson.annotations.SerializedName
import com.ria4.odoo.data.response.BaseRPCResult

/**
 * @param serverVersion major version
 * @param serverVersionInfo Full version
 *
 */
data class ServerVersion (
        @SerializedName("server_version")
        val serverVersion: String,
        @SerializedName("server_version_info")
        val serverVersionInfo: List<Any>,
        @SerializedName("server_serie")
        val serverSerie: String,
        @SerializedName("protocol_version")
        val protocolVersion: Int
): BaseRPCResult()