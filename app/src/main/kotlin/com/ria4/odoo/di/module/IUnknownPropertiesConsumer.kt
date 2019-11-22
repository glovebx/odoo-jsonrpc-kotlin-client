package com.ria4.odoo.di.module

import com.google.gson.JsonObject


interface IUnknownPropertiesConsumer {
    fun acceptUnknownProperties(jsonObject: JsonObject)
}