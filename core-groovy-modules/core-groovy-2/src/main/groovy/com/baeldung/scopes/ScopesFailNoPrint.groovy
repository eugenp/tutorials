package com.baeldung.scopes

import java.util.logging.Logger

logger = Logger.getLogger("ScopesFailNoPrint.groovy")

y = 2

def fLocal() {
    def q = 333
    println(q)
    q
}

logger.info(y.toString())
