package com.baeldung.observer.standard

import com.baeldung.observer.IObservable
import com.baeldung.observer.IObserver
import java.util.ArrayList

class BaeldungNewsletter : IObservable {
    override val observers: ArrayList<IObserver> = ArrayList()
    var newestArticleUrl = ""
        set(value) {
            field = value
            sendUpdateEvent()
        }
}