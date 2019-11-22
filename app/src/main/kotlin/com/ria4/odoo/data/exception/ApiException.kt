package com.ria4.odoo.data.exception;

const val UNKNOWN_EXCEPTION = -1001
const val NO_EXPECTED_DATA_EXCEPTION = -1002

open class ApiException: Throwable {
    val code: Int

    constructor(message:String?): super(message) {
        this.code = UNKNOWN_EXCEPTION
    }

    constructor(code:Int, message:String?): super(message) {
        this.code = code
    }
}

class NetworkErrorException: ApiException {
    constructor(message:String?): super(message)
}