package com.baeldung.scopes

x = 200

def getX() {
    return x;
}


def fGlobal() {
    z = 234
    println(z)
}

def fLocal() {
    def q = 333
    println(q)
}

println("- Global variable")
println(x)
println("- Access global variable from inside function")
println(getX())
println("- function called to create variable")
fGlobal()
println("- Variable created inside a function")
println(z)
println("- Access local variable from inside function")
fLocal()
println("- Local variable doesn't exist outside")
println(q)

