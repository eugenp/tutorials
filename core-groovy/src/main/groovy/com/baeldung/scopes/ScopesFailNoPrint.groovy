package com.baeldung.scopes

y = 2

def fLocal() {
    def q = 333
    println(q)
    q
}

