package com.baeldung.observer.delegates

import kotlin.properties.Delegates

class BaeldungNewsletter {
    val newestArticleObservers = mutableListOf<(String) -> Unit>()
    var newestArticleUrl: String by Delegates.observable("") { _, _, newValue ->
        newestArticleObservers.forEach { it(newValue) }
    }
}