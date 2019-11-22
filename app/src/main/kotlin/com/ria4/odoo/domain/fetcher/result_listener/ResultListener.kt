package com.ria4.odoo.domain.fetcher.result_listener

/**
 * Created by glovebx on 11.11.2019.
 */
interface ResultListener {

    fun onRequestStart(){}

    fun onRequestStart(requestType: RequestType){}

    fun onRequestError(errorMessage: String?){}

    fun onRequestError(requestType: RequestType, errorMessage: String?){}
}