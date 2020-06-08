package com.baeldung.scopes

import java.util.logging.Logger

logger = Logger.getLogger("ScopesFail.groovy")

y = 2

def fLocal() {
    def q = 333
    println(q)
    q
}

fLocal()

logger.info("- Local variable doesn't exist outside")
logger.info(q.toString())

