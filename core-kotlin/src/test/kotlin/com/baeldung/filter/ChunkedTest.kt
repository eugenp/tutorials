package com.baeldung.filter

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class ChunkedTest {

    @Test
    fun givenDNAFragmentString_whenChunking_thenProduceListOfChunks() {
        val dnaFragment = "ATTCGCGGCCGCCAA"

        val fragments = dnaFragment.chunked(3)

        assertIterableEquals(listOf("ATT", "CGC", "GGC", "CGC", "CAA"), fragments)
    }

    @Test
    fun givenDNAString_whenChunkingWithTransformer_thenProduceTransformedList() {
        val codonTable = mapOf("ATT" to "Isoleucine", "CAA" to "Glutamine", "CGC" to "Arginine", "GGC" to "Glycine")
        val dnaFragment = "ATTCGCGGCCGCCAA"

        val proteins = dnaFragment.chunked(3) { codon ->
            codonTable[codon.toString()] ?: error("Unknown codon")
        }

        assertIterableEquals(listOf("Isoleucine", "Arginine", "Glycine", "Arginine", "Glutamine"), proteins)
    }

    @Test
    fun givenListOfValues_whenChunking_thenProduceListOfArrays() {
        val whole = listOf(1, 4, 7, 4753, 2, 34, 62, 76, 5868, 0)
        val chunks = whole.chunked(6)

        val expected = listOf(listOf(1, 4, 7, 4753, 2, 34), listOf(62, 76, 5868, 0))

        assertIterableEquals(expected, chunks)
    }

}