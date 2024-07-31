package com.baeldung.roaringbitmap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.roaringbitmap.RoaringBitmap;

class RoaringBitmapBenchmarkUnitTest {
    @Test
    public void givenTwoRoaringBitmap_whenUsingOr_thenWillGetSetsUnion() {
        RoaringBitmap expected = RoaringBitmap.bitmapOf(1, 2, 3, 4, 5, 6, 7, 8);
        RoaringBitmap A = RoaringBitmap.bitmapOf(1, 2, 3, 4, 5);
        RoaringBitmap B = RoaringBitmap.bitmapOf(4, 5, 6, 7, 8);
        RoaringBitmap union = RoaringBitmap.or(A, B);
        assertEquals(expected, union);
    }

    @Test
    public void givenTwoRoaringBitmap_whenUsingAnd_thenWillGetSetsIntersection() {
        RoaringBitmap expected = RoaringBitmap.bitmapOf(4, 5);
        RoaringBitmap A = RoaringBitmap.bitmapOfRange(1, 6);
        RoaringBitmap B = RoaringBitmap.bitmapOf(4, 5, 6, 7, 8);
        RoaringBitmap intersection = RoaringBitmap.and(A, B);
        assertEquals(expected, intersection);
    }

    @Test
    public void givenTwoRoaringBitmap_whenUsingAndNot_thenWillGetSetsDifference() {
        RoaringBitmap expected = RoaringBitmap.bitmapOf(1, 2, 3);
        RoaringBitmap A = new RoaringBitmap();
        A.add(1L, 6L);
        RoaringBitmap B = RoaringBitmap.bitmapOf(4, 5, 6, 7, 8);
        RoaringBitmap difference = RoaringBitmap.andNot(A, B);
        assertEquals(expected, difference);
    }

    @Test
    public void givenTwoRoaringBitmap_whenUsingXOR_thenWillGetSetsSymmetricDifference() {
        RoaringBitmap expected = RoaringBitmap.bitmapOf(1, 2, 3, 6, 7, 8);
        RoaringBitmap A = RoaringBitmap.bitmapOfRange(1, 6);
        RoaringBitmap B = RoaringBitmap.bitmapOfRange(4, 9);
        RoaringBitmap xor = RoaringBitmap.xor(A, B);
        assertEquals(expected, xor);
    }
}