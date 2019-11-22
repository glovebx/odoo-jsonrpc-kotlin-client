package com.segment.jsonrpc

import com.ria4.odoo.data.exception.ApiException
import com.ria4.odoo.data.request.BaseRPCRequest
import com.ria4.odoo.data.response.BaseRPCResult
import java.io.IOException
import java.lang.reflect.Type
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

class JsonRPCConverterFactory private constructor()// Private constructor.
    : Converter.Factory() {

    override fun responseBodyConverter(type: Type?,
                                       annotations: Array<Annotation>?,
                                       retrofit: Retrofit?): Converter<ResponseBody, *>? {
        if (!Utils.isAnnotationPresent(annotations, JsonRPC::class.java)) {
            return null
        }

        val rpcType = Types.newParameterizedType(JsonRPCResponse::class.java, type!!)
        val delegate = retrofit!!.nextResponseBodyConverter<JsonRPCResponse<ResponseBody>>(this, rpcType, annotations!!)

        return JsonRPCResponseBodyConverter(delegate)
    }

    internal class JsonRPCResponseBodyConverter<T>(private val delegate: Converter<ResponseBody, JsonRPCResponse<T>>) : Converter<ResponseBody, T> {

        @Throws(IOException::class, ApiException::class)
        override fun convert(responseBody: ResponseBody): T? {
            val response = delegate.convert(responseBody)
            if (response.error != null) {
                val error = response.error as Map<String, Any>
                val code: Int = error["code"].toString().toInt()
                val message = error["message"].toString()
                throw ApiException(code, message)
            }
            return response.result
        }
    }

    override fun requestBodyConverter(type: Type?, annotations: Array<Annotation>?,
                                      methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        val methodAnnotation = Utils.findAnnotation(methodAnnotations, JsonRPC::class.java)
                ?: return null
        val method = methodAnnotation.value

        val delegate = retrofit!!.nextRequestBodyConverter<JsonRPCRequest>(this, JsonRPCRequest::class.java, annotations!!,
                methodAnnotations!!)

        return JsonRPCRequestBodyConverter<BaseRPCRequest>(method, delegate)
    }

    internal class JsonRPCRequestBodyConverter<T>(private val method: String, private val delegate: Converter<JsonRPCRequest, RequestBody>) : Converter<T, RequestBody> {

        @Throws(IOException::class)
        override fun convert(value: T): RequestBody {
            return delegate.convert(JsonRPCRequest.create(method, value as BaseRPCRequest))
        }
    }

    companion object {
        fun create(): JsonRPCConverterFactory {
            return JsonRPCConverterFactory()
        }
    }
}
