package com.ria4.odoo.domain.entity

/**
 * @param serverVersion major version
 * @param serverVersionInfo Full version
 *
 */
data class ServerVersion (
        val serverVersion: String,
        val serverVersionInfo: String,
        val serverSerie: String,
        val protocolVersion: Int
)