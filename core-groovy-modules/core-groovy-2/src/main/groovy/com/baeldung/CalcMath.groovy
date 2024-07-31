package com.baeldung

import org.slf4j.LoggerFactory

class CalcMath {
    def log = LoggerFactory.getLogger(this.getClass())

    def calcSum(x, y) {
        log.info "Executing $x + $y"
        x + y
    }

    /**
     * example of method that in java would throw error at compile time
     * @param x
     * @param y
     * @return
     */
    def calcSum2(x, y) {
        log.info "Executing $x + $y"
        // DANGER! This won't throw a compilation issue and fail only at runtime!!!
        calcSum3()
        log.info("Logging an undefined variable: $z")
    }
}