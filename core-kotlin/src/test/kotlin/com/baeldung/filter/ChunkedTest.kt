package com.baeldung.filter

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class ChunkedTest {

    @Test
    fun givenMapOfCodonsAndDNAFragment_whenChunkingDNAStringWithAndWithoutTransformer_thenProduceListOfChunksAndMappedTransforms() {
        val codonTable = mapOf("ATT" to "Isoleucine", "CAA" to "Glutamine", "CGC" to "Arginine", "GGC" to "Glycine")
        val dnaFragment = "ATTCGCGGCCGCCAA"

        val fragments = dnaFragment.chunked(3)
        val proteins = dnaFragment.chunked(3) { codon: CharSequence ->
            codonTable[codon.toString()] ?: error("Unknown codon")
        }

        assertTrue { listOf("ATT", "CGC", "GGC", "CGC", "CAA") == fragments }
        assertTrue { proteins == listOf("Isoleucine", "Arginine", "Glycine", "Arginine", "Glutamine") }
    }
}