package com.baeldung.collections.bitset;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.BitSet;

import static org.assertj.core.api.Assertions.assertThat;

public class BitSetUnitTest {

    @Test
    public void givenBoolArray_whenMemoryLayout_thenConsumeMoreThanOneBit() {
        boolean[] bits = new boolean[1024];

        System.out.println(ClassLayout.parseInstance(bits).toPrintable());
    }

    @Test
    public void givenBitSet_whenMemoryLayout_thenConsumeObeBitPerFlag() {
        BitSet bitSet = new BitSet(1024);

        System.out.println(GraphLayout.parseInstance(bitSet).toPrintable());
    }

    @Test
    public void givenBitSet_whenSetting_thenShouldBeTrue() {
        BitSet bitSet = new BitSet();

        bitSet.set(10);
        assertThat(bitSet.get(10)).isTrue();

        bitSet.set(20, 30);
        for (int i = 20; i <= 29; i++) assertThat(bitSet.get(i)).isTrue();
        assertThat(bitSet.get(30)).isFalse();

        bitSet.set(10, false);
        assertThat(bitSet.get(10)).isFalse();

        bitSet.set(20, 30, false);
        for (int i = 20; i <= 30; i++) assertThat(bitSet.get(i)).isFalse();
    }

    @Test
    public void givenBitSet_whenClearing_thenShouldBeFalse() {
        BitSet bitSet = new BitSet();
        bitSet.set(42);
        assertThat(bitSet.get(42)).isTrue();

        bitSet.clear(42);
        assertThat(bitSet.get(42)).isFalse();

        bitSet.set(10, 20);
        for (int i = 10; i < 20; i++) assertThat(bitSet.get(i)).isTrue();

        bitSet.clear(10, 20);
        for (int i = 10; i < 20; i++) assertThat(bitSet.get(i)).isFalse();

        bitSet.set(10, 20);
        bitSet.clear();
        for (int i = 0; i < 100; i++) assertThat(bitSet.get(i)).isFalse();
    }

    @Test
    public void givenBitSet_whenGettingElements_thenShouldReturnRequestedBits() {
        BitSet bitSet = new BitSet();
        bitSet.set(42);

        assertThat(bitSet.get(42)).isTrue();
        assertThat(bitSet.get(43)).isFalse();

        bitSet.set(10, 20);
        BitSet newBitSet = bitSet.get(10, 20);
        for (int i = 0; i < 10; i++) assertThat(newBitSet.get(i)).isTrue();
    }

    @Test
    public void givenBitSet_whenFlip_thenTogglesTrueToFalseAndViceVersa() {
        BitSet bitSet = new BitSet();
        bitSet.set(42);
        bitSet.flip(42);
        assertThat(bitSet.get(42)).isFalse();

        bitSet.flip(12);
        assertThat(bitSet.get(12)).isTrue();

        bitSet.flip(30, 40);
        for (int i = 30; i < 40; i++) assertThat(bitSet.get(i)).isTrue();
    }

    @Test
    public void givenBitSet_whenGettingTheSize_thenReturnsTheSize() {
        BitSet defaultBitSet = new BitSet();
        assertThat(defaultBitSet.size()).isEqualTo(64);

        BitSet bitSet = new BitSet(1024);
        assertThat(bitSet.size()).isEqualTo(1024);

        assertThat(bitSet.cardinality()).isEqualTo(0);
        bitSet.set(10, 30);
        assertThat(bitSet.cardinality()).isEqualTo(30 - 10);

        assertThat(bitSet.length()).isEqualTo(30);
        bitSet.set(100);
        assertThat(bitSet.length()).isEqualTo(101);

        assertThat(bitSet.isEmpty()).isFalse();
        bitSet.clear();
        assertThat(bitSet.isEmpty()).isTrue();
    }

    @Test
    public void givenBitSet_whenSetOperations_thenShouldReturnAnotherBitSet() {
        BitSet first = new BitSet();
        first.set(5, 10);

        BitSet second = new BitSet();
        second.set(7, 15);

        assertThat(first.intersects(second)).isTrue();

        first.and(second);
        assertThat(first.get(7)).isTrue();
        assertThat(first.get(8)).isTrue();
        assertThat(first.get(9)).isTrue();
        assertThat(first.get(10)).isFalse();
    }

    @Test
    public void givenBitSet_whenStream_thenStreamsAllSetBits() {
        BitSet bitSet = new BitSet();
        bitSet.set(15, 25);

        bitSet.stream().forEach(System.out::println);
    }

    @Test
    public void givenBitSet_whenNextOrPrev_thenReturnsTheNextClearOrSetBit() {
        BitSet bitSet = new BitSet();
        bitSet.set(15, 25);

        assertThat(bitSet.nextSetBit(13)).isEqualTo(15);
        assertThat(bitSet.nextSetBit(25)).isEqualTo(-1);

        assertThat(bitSet.nextClearBit(23)).isEqualTo(25);

        assertThat(bitSet.previousClearBit(24)).isEqualTo(14);
        assertThat(bitSet.previousSetBit(29)).isEqualTo(24);
        assertThat(bitSet.previousSetBit(14)).isEqualTo(-1);
    }
}
