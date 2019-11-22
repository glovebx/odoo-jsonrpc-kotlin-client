package com.segment.jsonrpc

import java.lang.annotation.Retention

import java.lang.annotation.ElementType.METHOD
import java.lang.annotation.RetentionPolicy.RUNTIME

/** Make a JSON-RPC request.  */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RUNTIME)
annotation class JsonRPC(
        /** The name of RPC method being invoked by this call.  */
        val value: String = "")
