@file:JvmName("Strings")
package com.baeldung.kotlin

fun String.escapeForXml() : String {
    return this
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
}
