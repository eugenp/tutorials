package com.baeldung.scopes

import java.util.logging.Logger

x = 200
logger = Logger.getLogger("Scopes.groovy")

def getGlobalResult() {
    logger.info(x.toString())
    def test = 1 + x
    return test
}

def getGlobalCreatedLocally() {
    z = 234
    logger = Logger.getLogger("Scopes.groovy")
    logger.info(z.toString())
    return z
}

logger.info("- Global variable")
logger.info(x.toString())
logger.info("- Access global variable from inside function")
logger.info(getGlobalResult().toString())
logger.info("- function called to create variable")
getGlobalCreatedLocally()
logger.info("- Variable created inside a function")
logger.info(z.toString())
