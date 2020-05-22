package com.baeldung.scopes

x = 200

def getGlobalResult() {
    println(x)
    def test = 1 + x
    return   test
}


def getGlobalCreatedLocally() {
    z = 234
    println(z)
    return z
}

println("- Global variable")
println(x)
println("- Access global variable from inside function")
println(getGlobalResult())
println("- function called to create variable")
getGlobalCreatedLocally()
println("- Variable created inside a function")
println(z)
