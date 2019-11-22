package com.ria4.odoo.di.module

import com.google.gson.TypeAdapterFactory
import com.google.gson.FieldNamingStrategy
import com.google.gson.internal.Excluder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException


class UnknownPropertiesTypeAdapterFactory: TypeAdapterFactory {
    private constructor() {}

    companion object {
        val instance: UnknownPropertiesTypeAdapterFactory by lazy {
            UnknownPropertiesTypeAdapterFactory()
        }
    }

    override fun <T> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T>? {
        // Check if we can deal with the given type
        if (!IUnknownPropertiesConsumer::class.java.isAssignableFrom(typeToken.rawType)) {
            return null
        }
        // If we can, we should get the backing class to fetch its fields from
        val rawType = typeToken.rawType as Class<IUnknownPropertiesConsumer>
        val delegateTypeAdapter = gson.getDelegateAdapter(this, typeToken) as TypeAdapter<IUnknownPropertiesConsumer>
        // Excluder is necessary to check if the field can be processed
        // Basically it's not required, but it makes the check more complete
        val excluder = gson.excluder()
        // This is crucial to map fields and JSON object properties since Gson supports name remapping
        val fieldNamingStrategy = gson.fieldNamingStrategy()

        return UnknownPropertiesTypeAdapter.create<IUnknownPropertiesConsumer>(rawType, delegateTypeAdapter, excluder, fieldNamingStrategy) as TypeAdapter<T>
    }

    private class UnknownPropertiesTypeAdapter<T : IUnknownPropertiesConsumer> private constructor(private val typeAdapter: TypeAdapter<T>, private val propertyNames: Collection<String>) : TypeAdapter<T>() {

        companion object {
            fun <T : IUnknownPropertiesConsumer> create(clazz: Class<in T>
                                                        , typeAdapter: TypeAdapter<T>
                                                        , excluder: Excluder
                                                        , fieldNamingStrategy: FieldNamingStrategy): TypeAdapter<T> {
                val propertyNames = getPropertyNames(clazz, excluder, fieldNamingStrategy)
                return UnknownPropertiesTypeAdapter(typeAdapter, propertyNames)
            }

            fun getPropertyNames(clazz: Class<*>, excluder: Excluder, fieldNamingStrategy: FieldNamingStrategy): Collection<String> {
                val propertyNames = arrayListOf<String>()
                // Class fields are declared per class so we have to traverse the whole hierarachy
                var i = clazz
                while (i.superclass != null && i != Any::class.java) {
                    for (declaredField in i.declaredFields) {
                        // If the class field is not excluded
                        if (!excluder.excludeField(declaredField, false)) {
                            // We can translate the field name to its property name counter-part
                            val propertyName = fieldNamingStrategy.translateName(declaredField)
                            propertyNames.add(propertyName)
                        }
                    }
                    i = i.superclass
                }
                return propertyNames
            }
        }

        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: T) {
            typeAdapter.write(out, value)
        }

        override fun read(`in`: JsonReader): T {
            // JsonParser holds no state so instantiation is a bit excessive, but Gson may change in the future
            val jsonParser = JsonParser()
            // In its simplest solution, we can just collect a JSON tree because its much easier to process
            val jsonObjectToParse = jsonParser.parse(`in`).asJsonObject
            val unknownProperties = JsonObject()
            for (e in jsonObjectToParse.entrySet()) {
                val propertyName = e.key
                // No in the object fields?
                if (!propertyNames.contains(propertyName)) {
                    // Then we assume the property is unknown
                    unknownProperties.add(propertyName, e.value)
                }
            }
            // First convert the above JSON tree to an object
            val obj = typeAdapter.fromJsonTree(jsonObjectToParse)
            // And do the post-processing
            obj.acceptUnknownProperties(unknownProperties)
            return obj
        }

//        private fun getPropertyNames(clazz: Class<*>, excluder: Excluder, fieldNamingStrategy: FieldNamingStrategy): Collection<String> {
//            val propertyNames = arrayListOf<String>()
//            // Class fields are declared per class so we have to traverse the whole hierarachy
//            var i = clazz
//            while (i.superclass != null && i != Any::class.java) {
//                for (declaredField in i.declaredFields) {
//                    // If the class field is not excluded
//                    if (!excluder.excludeField(declaredField, false)) {
//                        // We can translate the field name to its property name counter-part
//                        val propertyName = fieldNamingStrategy.translateName(declaredField)
//                        propertyNames.add(propertyName)
//                    }
//                }
//                i = i.superclass
//            }
//            return propertyNames
//        }

    }
}