package com.baeldung.observer.delegates

fun main() {
    val newsletter = BaeldungNewsletter()
    newsletter.newestArticleObservers.add { newestArticleUrl ->
        println("New Baeldung article: ${newestArticleUrl}")
    }
}