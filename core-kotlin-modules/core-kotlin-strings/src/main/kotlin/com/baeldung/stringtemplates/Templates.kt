package com.baeldung.stringtemplates

/**
 * Example of a useful function defined in Kotlin String class
 */
fun padExample(): String {
	return "Hello".padEnd(10, '!')
}

/**
 * Example of a simple string template usage
 */
fun simpleTemplate(n: Int): String {
	val message = "n = $n"
	return message
}

/**
 * Example of a string template with a simple expression
 */
fun templateWithExpression(n: Int): String {
	val message = "n + 1 = ${n + 1}"
	return message
}

/**
 * Example of a string template with expression containing some logic
 */
fun templateWithLogic(n: Int): String {
	val message = "$n is ${if (n > 0) "positive" else "not positive"}"
	return message
}

/**
 * Example of nested string templates
 */
fun nestedTemplates(n: Int): String {
	val message = "$n is ${if (n > 0) "positive" else if (n < 0) "negative and ${if (n % 2 == 0) "even" else "odd"}" else "zero"}"
	return message
}

/**
 * Example of joining array's element into a string with a default separator
 */
fun templateJoinArray(): String {
	val numbers = listOf(1, 1, 2, 3, 5, 8)
	val message = "first Fibonacci numbers: ${numbers.joinToString()}"
	return message
}

/**
 * Example of escaping the dollar sign
 */
fun notAStringTemplate(): String {
	val message = "n = \$n"
	return message
}

/**
 * Example of a simple triple quoted string
 */
fun showFilePath(): String {
	val path = """C:\Repository\read.me"""
	return path
}

/**
 * Example of a multiline string
 */
fun showMultiline(): String {
	val receipt = """Item 1: $1.00
Item 2: $0.50"""
	return receipt
}

/**
 * Example of a multiline string with indentation
 */
fun showMultilineIndent(): String {
	val receipt = """Item 1: $1.00
                        >Item 2: $0.50""".trimMargin(">")
	return receipt
}

/**
 * Example of a triple quoted string with a not-working escape sequence
 */
fun showTripleQuotedWrongEscape(): String {
	val receipt = """Item 1: $1.00\nItem 2: $0.50"""
	return receipt
}

/**
 * Example of a triple quoted string with a correctly working escape sequence
 */

fun showTripleQuotedCorrectEscape(): String {
	val receipt = """Item 1: $1.00${"\n"}Item 2: $0.50"""
	return receipt
}

fun main(args: Array<String>) {
	println(padExample())
	println(simpleTemplate(10))
	println(templateWithExpression(5))
	println(templateWithLogic(7))
	println(nestedTemplates(-5))
	println(templateJoinArray())
	println(notAStringTemplate())
	println(showFilePath())
	println(showMultiline())
	println(showMultilineIndent())
	println(showTripleQuotedWrongEscape())
	println(showTripleQuotedCorrectEscape())
}
