package com.baeldung.regex

import org.junit.Test
import kotlin.test.*

class RegexTest {

    @Test
    fun whenRegexIsInstantiated_thenIsEqualToToRegexMethod() {
        val pattern = """a([bc]+)d?\\"""

        assertEquals(Regex.fromLiteral(pattern).pattern, pattern)
        assertEquals(pattern, Regex(pattern).pattern)
        assertEquals(pattern, pattern.toRegex().pattern)
    }

    @Test
    fun whenRegexMatches_thenResultIsTrue() {
        val regex = """a([bc]+)d?""".toRegex()

        assertTrue(regex.containsMatchIn("xabcdy"))
        assertTrue(regex.matches("abcd"))
        assertFalse(regex matches "xabcdy")
    }

    @Test
    fun givenCompletelyMatchingRegex_whenMatchResult_thenDestructuring() {
        val regex = """a([bc]+)d?""".toRegex()

        assertNull(regex.matchEntire("xabcdy"))

        val matchResult = regex.matchEntire("abbccbbd")

        assertNotNull(matchResult)
        assertEquals(matchResult!!.value, matchResult.groupValues[0])
        assertEquals(matchResult.destructured.toList(), matchResult.groupValues.drop(1))
        assertEquals("bbccbb", matchResult.destructured.component1())
        assertNull(matchResult.next())
    }

    @Test
    fun givenPartiallyMatchingRegex_whenMatchResult_thenGroups() {
        val regex = """a([bc]+)d?""".toRegex()
        var matchResult = regex.find("abcb abbd")

        assertNotNull(matchResult)
        assertEquals(matchResult!!.value, matchResult.groupValues[0])
        assertEquals("abcb", matchResult.value)
        assertEquals(IntRange(0, 3), matchResult.range)
        assertEquals(listOf("abcb", "bcb"), matchResult.groupValues)
        assertEquals(matchResult.destructured.toList(), matchResult.groupValues.drop(1))

        matchResult = matchResult.next()

        assertNotNull(matchResult)
        assertEquals("abbd", matchResult!!.value)
        assertEquals("bb", matchResult.groupValues[1])

        matchResult = matchResult.next()

        assertNull(matchResult)
    }

    @Test
    fun givenPartiallyMatchingRegex_whenMatchResult_thenDestructuring() {
        val regex = """([\w\s]+) is (\d+) years old""".toRegex()
        val matchResult = regex.find("Mickey Mouse is 95 years old")
        val (name, age) = matchResult!!.destructured

        assertEquals("Mickey Mouse", name)
        assertEquals("95", age)
    }

    @Test
    fun givenNonMatchingRegex_whenFindCalled_thenNull() {
        val regex = """a([bc]+)d?""".toRegex()
        val matchResult = regex.find("foo")

        assertNull(matchResult)
    }

    @Test
    fun givenNonMatchingRegex_whenFindAllCalled_thenEmptySet() {
        val regex = """a([bc]+)d?""".toRegex()
        val matchResults = regex.findAll("foo")

        assertNotNull(matchResults)
        assertTrue(matchResults.none())
    }

    @Test
    fun whenReplace_thenReplacement() {
        val regex = """(red|green|blue)""".toRegex()
        val beautiful = "Roses are red, Violets are blue"
        val grim = regex.replace(beautiful, "dark")
        val shiny = regex.replaceFirst(beautiful, "rainbow")

        assertEquals("Roses are dark, Violets are dark", grim)
        assertEquals("Roses are rainbow, Violets are blue", shiny)
    }

    @Test
    fun whenComplexReplace_thenReplacement() {
        val regex = """(red|green|blue)""".toRegex()
        val beautiful = "Roses are red, Violets are blue"
        val reallyBeautiful = regex.replace(beautiful) {
            matchResult ->  matchResult.value.toUpperCase() + "!"
        }

        assertEquals("Roses are RED!, Violets are BLUE!", reallyBeautiful)
    }

    @Test
    fun whenSplit_thenList() {
        val regex = """\W+""".toRegex()
        val beautiful = "Roses are red, Violets are blue"

        assertEquals(listOf("Roses", "are", "red", "Violets", "are", "blue"), regex.split(beautiful))
        assertEquals(listOf("Roses", "are", "red", "Violets are blue"), regex.split(beautiful, 4))
        assertEquals(regex.toPattern().split(beautiful).asList(), regex.split(beautiful))
    }

}