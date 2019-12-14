package com.baeldung.jvmannotations

import java.util.*
class TextDocument : Document {
    override fun getType() = "text"

    fun transformList(list : List<Number>) : List<Number> {
        return list.filter { n -> n.toInt() > 1 }
    }

    fun transformListInverseWildcards(list : List<@JvmSuppressWildcards Number>) : List<@JvmWildcard Number> {
        return list.filter { n -> n.toInt() > 1 }
    }

    var list : List<@JvmWildcard Any> = ArrayList()
}
