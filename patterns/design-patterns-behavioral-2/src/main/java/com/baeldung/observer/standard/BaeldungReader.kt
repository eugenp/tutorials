package com.baeldung.observer.standard

import com.baeldung.observer.IObserver

class BaeldungReader(private var newsletter: BaeldungNewsletter) : IObserver {
    override fun update() {
        println("New Baeldung article: ${newsletter.newestArticleUrl}")
    }
}