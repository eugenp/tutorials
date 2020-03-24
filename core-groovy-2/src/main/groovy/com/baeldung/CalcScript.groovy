package com.baeldung

def calcSum(x, y) {
    x + y
}

def calcSum2(x, y) {
    // DANGER! The variable "log" may be undefined
    log.info "Executing $x + $y"
    // DANGER! This method doesn't exist!
    calcSum3()
    // DANGER! The logged variable "z" is undefined!
    log.info("Logging an undefined variable: $z")
}

calcSum(1,5)
