package com.baeldung.scopes

x = 2

def getX() {
    return x;
}


def f() {
    z = 23
    println(z)
}

println(x)
def scopes = new Scopes();
print(getX())

f()
println(z)