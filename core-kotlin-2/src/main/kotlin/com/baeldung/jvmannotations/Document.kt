package com.baeldung.jvmannotations

import java.util.*

interface Document {

    @JvmDefault
    fun getType() = "document"
}

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

class XmlDocument(d : Document) : Document by d

fun main() {
    val myDocument = TextDocument()
    val myTextDocument = XmlDocument(myDocument)
    println("${myDocument.getType()} ${myTextDocument.getType()}")
}
