package com.baeldung.assertj;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.common.collect.TreeRangeMap;
import com.google.common.io.Files;
import org.assertj.guava.data.MapEntry;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import static org.assertj.guava.api.Assertions.assertThat;
import static org.assertj.guava.api.Assertions.entry;

public class AssertJGuavaUnitTest {

    @Test
    public void givenTwoEmptyFiles_whenComparingContent_thenEqual() throws Exception {
        final File temp1 = File.createTempFile("bael", "dung1");
        final File temp2 = File.createTempFile("bael", "dung2");

        assertThat(Files.asByteSource(temp1)).hasSize(0).hasSameContentAs(Files.asByteSource(temp2));
    }

    @Test
    public void givenMultimap_whenVerifying_thenCorrect() throws Exception {
        final Multimap<Integer, String> mmap = ArrayListMultimap.create();
        mmap.put(1, "one");
        mmap.put(1, "1");

        assertThat(mmap).hasSize(2).containsKeys(1).contains(entry(1, "one")).contains(entry(1, "1"));
    }

    @Test
    public void givenMultimaps_whenVerifyingContent_thenCorrect() throws Exception {
        final Multimap<Integer, String> mmap1 = ArrayListMultimap.create();
        mmap1.put(1, "one");
        mmap1.put(1, "1");
        mmap1.put(2, "two");
        mmap1.put(2, "2");

        final Multimap<Integer, String> mmap1_clone = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);
        mmap1_clone.put(1, "one");
        mmap1_clone.put(1, "1");
        mmap1_clone.put(2, "two");
        mmap1_clone.put(2, "2");

        final Multimap<Integer, String> mmap2 = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);
        mmap2.put(1, "one");
        mmap2.put(1, "1");

        assertThat(mmap1).containsAllEntriesOf(mmap2).containsAllEntriesOf(mmap1_clone).hasSameEntriesAs(mmap1_clone);
    }

    @Test
    public void givenOptional_whenVerifyingContent_thenShouldBeEqual() throws Exception {
        final Optional<String> something = Optional.of("something");

        assertThat(something).isPresent().extractingValue().isEqualTo("something");
    }

    @Test
    public void givenRange_whenVerifying_thenShouldBeCorrect() throws Exception {
        final Range<String> range = Range.openClosed("a", "g");

        assertThat(range).hasOpenedLowerBound().isNotEmpty().hasClosedUpperBound().contains("b");
    }

    @Test
    public void givenRangeMap_whenVerifying_thenShouldBeCorrect() throws Exception {
        final TreeRangeMap<Integer, String> map = TreeRangeMap.create();

        map.put(Range.closed(0, 60), "F");
        map.put(Range.closed(61, 70), "D");

        assertThat(map).isNotEmpty().containsKeys(0).contains(MapEntry.entry(34, "F"));
    }

    @Test
    public void givenTable_whenVerifying_thenShouldBeCorrect() throws Exception {
        final Table<Integer, String, String> table = HashBasedTable.create(2, 2);

        table.put(1, "A", "PRESENT");
        table.put(1, "B", "ABSENT");

        assertThat(table).hasRowCount(1).containsValues("ABSENT").containsCell(1, "B", "ABSENT");
    }

}
