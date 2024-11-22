package main

/*
#include <stdlib.h>
*/
import "C"
import "fmt"

//export SayHello
func SayHello() {
	fmt.Println("Hello Baeldung from Go!")
}

//export AddNumbers
func AddNumbers(a, b C.int) C.int {
	return a + b
}

//export Greet
func Greet(name *C.char) *C.char {
	greeting := fmt.Sprintf("Hello, %s!", C.GoString(name))
	return C.CString(greeting)
}

func main() {}