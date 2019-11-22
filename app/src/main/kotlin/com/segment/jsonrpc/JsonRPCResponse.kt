package com.segment.jsonrpc

internal class JsonRPCResponse<T> {
    var id: Long = 0
    var result: T? = null
    var error: Any? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as JsonRPCResponse<*>?

        if (id != that!!.id) return false
        return if (if (result != null) result != that.result else that.result != null) false else !if (error != null) error != that.error else that.error != null
    }

    override fun hashCode(): Int {
        var result1 = (id xor id.ushr(32)).toInt()
        result1 = 31 * result1 + if (result != null) result!!.hashCode() else 0
        result1 = 31 * result1 + if (error != null) error!!.hashCode() else 0
        return result1
    }
}
