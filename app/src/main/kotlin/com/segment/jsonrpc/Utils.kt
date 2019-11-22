package com.segment.jsonrpc

internal class Utils private constructor() {
    init {
        throw AssertionError("No instances")
    }

    companion object {

        /** Returns true if `annotations` contains an instance of `cls`.  */
        fun <T : Annotation> isAnnotationPresent(annotations: Array<Annotation>?,
                                                 cls: Class<T>): Boolean {
            return findAnnotation(annotations, cls) != null
        }

        /** Returns an instance of `cls` if `annotations` contains an instance.  */
        fun <T : Annotation> findAnnotation(annotations: Array<Annotation>?,
                                            cls: Class<T>): T? {
            annotations?.run {
                for (annotation in this) {
                    if (cls.isInstance(annotation)) {

                        return annotation as T
                    }
                }
            }

            return null
        }
    }
}
