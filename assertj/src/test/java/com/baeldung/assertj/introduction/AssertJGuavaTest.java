package com.baeldung.assertj.introduction;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeRangeMap;
import com.google.common.io.Files;
import org.assertj.guava.data.MapEntry;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

import static org.assertj.guava.api.Assertions.assertThat;
import static org.assertj.guava.api.Assertions.entry;

public class AssertJGuavaTest {

    @Test
    public void givenTwoEmptyFiles_whenComparingContent_thenEqual() throws Exception {
        final File temp = File.createTempFile("bael", "dung");
        final File temp2 = File.createTempFile("bael", "dung2");

        assertThat(Files.asByteSource(temp))
                .hasSize(0)
                .hasSameContentAs(Files.asByteSource(temp2));
    }

    @Test
    public void givenMultimap_whenVerifying_thenCorrect() throws Exception {
        final Multimap<Integer, String> mmap = Multimaps.newMultimap(new HashMap<>(), Sets::newHashSet);
        mmap.put(1, "one");
        mmap.put(1, "1");

        assertThat(mmap)
                .hasSize(2)
                .containsKeys(1)
                .contains(entry(1, "one"))
                .contains(entry(1, "1"));
    }

    @Test
    public void givenOptional_whenVerifyingContent_thenShouldBeEqual() throws Exception {
        final Optional<String> something = Optional.of("something");

        assertThat(something)
                .isPresent()
                .extractingValue()
                .isEqualTo("something");
    }

    @Test
    public void givenRange_whenVerifying_thenShouldBeCorrect() throws Exception {
        final Range<String> range = Range.openClosed("a", "g");

        assertThat(range)
                .hasOpenedLowerBound()
                .isNotEmpty()
                .hasClosedUpperBound()
                .contains("b");
    }

    @Test
    public void givenRangeMap_whenVerifying_thenShouldBeCorrect() throws Exception {
        final TreeRangeMap<Integer, String> map = TreeRangeMap.create();

        map.put(Range.closed(0, 60), "F");
        map.put(Range.closed(61, 70), "D");

        assertThat(map)
                .isNotEmpty()
                .containsKeys(0)
                .contains(MapEntry.entry(34, "F"));
    }

    @Test
    public void givenTable_whenVerifying_thenShouldBeCorrect() throws Exception {
        final HashBasedTable<Integer, String, String> table = HashBasedTable.create(2, 2);

        table.put(1, "A", "PRESENT");
        table.put(1, "B", "ABSENT");

        assertThat(table)
                .hasRowCount(1)
                .containsValues("ABSENT")
                .containsCell(1, "B", "ABSENT");
    }




}
