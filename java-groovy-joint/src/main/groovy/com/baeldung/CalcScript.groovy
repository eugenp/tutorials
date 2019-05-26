package com.baeldung

def calcSum(x, y) {
    x + y
}

def calcSum2(x, y) {
    // DANGER! This won't throw a compilation issue and fail only at runtime!!!
    calcSum3()
}
