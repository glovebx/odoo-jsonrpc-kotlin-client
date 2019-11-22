package com.segment.jsonrpc

import com.ria4.odoo.data.request.BaseRPCRequest
import java.util.Random
import kotlin.math.abs

internal class JsonRPCRequest(private val method: String
                              , private val params: BaseRPCRequest
                              , val id: Long) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as JsonRPCRequest?

        if (id != that!!.id) return false
        return if (method != that.method) false else params == that.params
    }

    override fun hashCode(): Int {
        var result = method.hashCode()
        result = 31 * result + params.hashCode()
        result = 31 * result + (id xor id.ushr(32)).toInt()
        return result
    }

    companion object {
        private val RANDOM = Random()

        // {"jsonrpc": "1.0", "method":"call", "params":{	"context": {}, "args":[], "method": "version", "service": "common"}, "id":1}
        fun create(method: String, args: BaseRPCRequest): JsonRPCRequest {
            val id = abs(RANDOM.nextLong())
//            return JsonRPCRequest(method, listOf(args), id, "2.0")
            return JsonRPCRequest(method, args, id)
        }
    }
}
