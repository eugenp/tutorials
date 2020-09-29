package com.baeldung.arguments

fun main() {
    resizePane(newSize = 10, forceResize = true, noAnimation = true)

    // Swap the order of last two named arguments
    resizePane(newSize = 11, noAnimation = true, forceResize = true)

    // Named arguments can be passed in any order
    resizePane(forceResize = true, newSize = 12, noAnimation = true)

    // Mixing Named and Positional Arguments
    // Using a positional argument in the middle of named arguments
    resizePane(newSize = 20, true, noAnimation = true)

    // Only the last argument as a positional argument
    resizePane(newSize = 30, forceResize = true, true)

    // Use a named argument in the middle of positional arguments
    resizePane(40, forceResize = true, true)
}

fun resizePane(newSize: Int, forceResize: Boolean, noAnimation: Boolean) {
    println("The parameters are newSize = $newSize, forceResize = $forceResize, noAnimation = $noAnimation")
}