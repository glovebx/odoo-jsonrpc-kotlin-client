package com.ria4.odoo.presentation.utils.extensions

import java.io.*

fun File.hasContent(): Boolean {
    return this.exists() && this.length() > 0L
}