package com.baeldung.guava.table;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

public class GuavaTableUnitTest {

    @Test
    public void givenTable_whenGet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final int seatCount = universityCourseSeatTable.get("Mumbai", "IT");
        final Integer seatCountForNoEntry = universityCourseSeatTable.get("Oxford", "IT");

        assertThat(seatCount).isEqualTo(60);
        assertThat(seatCountForNoEntry).isEqualTo(null);
    }

    @Test
    public void givenTable_whenContains_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final boolean entryIsPresent = universityCourseSeatTable.contains("Mumbai", "IT");
        final boolean entryIsAbsent = universityCourseSeatTable.contains("Oxford", "IT");
        final boolean courseIsPresent = universityCourseSeatTable.containsColumn("IT");
        final boolean universityIsPresent = universityCourseSeatTable.containsRow("Mumbai");
        final boolean seatCountIsPresent = universityCourseSeatTable.containsValue(60);

        assertThat(entryIsPresent).isEqualTo(true);
        assertThat(entryIsAbsent).isEqualTo(false);
        assertThat(courseIsPresent).isEqualTo(true);
        assertThat(universityIsPresent).isEqualTo(true);
        assertThat(seatCountIsPresent).isEqualTo(true);
    }

    @Test
    public void givenTable_whenRemove_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);

        final int seatCount = universityCourseSeatTable.remove("Mumbai", "IT");

        assertThat(seatCount).isEqualTo(60);
        assertThat(universityCourseSeatTable.remove("Mumbai", "IT")).isEqualTo(null);
    }

    @Test
    public void givenTable_whenColumn_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final Map<String, Integer> universitySeatMap = universityCourseSeatTable.column("IT");

        assertThat(universitySeatMap).hasSize(2);
        assertThat(universitySeatMap.get("Mumbai")).isEqualTo(60);
        assertThat(universitySeatMap.get("Harvard")).isEqualTo(120);
    }

    @Test
    public void givenTable_whenColumnMap_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final Map<String, Map<String, Integer>> courseKeyUniversitySeatMap = universityCourseSeatTable.columnMap();

        assertThat(courseKeyUniversitySeatMap).hasSize(3);
        assertThat(courseKeyUniversitySeatMap.get("IT")).hasSize(2);
        assertThat(courseKeyUniversitySeatMap.get("Electrical")).hasSize(1);
        assertThat(courseKeyUniversitySeatMap.get("Chemical")).hasSize(1);
    }

    @Test
    public void givenTable_whenRow_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final Map<String, Integer> courseSeatMap = universityCourseSeatTable.row("Mumbai");

        assertThat(courseSeatMap).hasSize(2);
        assertThat(courseSeatMap.get("IT")).isEqualTo(60);
        assertThat(courseSeatMap.get("Chemical")).isEqualTo(120);
    }

    @Test
    public void givenTable_whenRowKeySet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final Set<String> universitySet = universityCourseSeatTable.rowKeySet();

        assertThat(universitySet).hasSize(2);
    }

    @Test
    public void givenTable_whenColKeySet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final Set<String> courseSet = universityCourseSeatTable.columnKeySet();

        assertThat(courseSet).hasSize(3);
    }

    @Test
    public void givenTreeTable_whenGet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = TreeBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final int seatCount = universityCourseSeatTable.get("Mumbai", "IT");

        assertThat(seatCount).isEqualTo(60);
    }

    @Test
    public void givenImmutableTable_whenGet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = ImmutableTable.<String, String, Integer> builder()
            .put("Mumbai", "Chemical", 120)
            .put("Mumbai", "IT", 60)
            .put("Harvard", "Electrical", 60)
            .put("Harvard", "IT", 120)
            .build();

        final int seatCount = universityCourseSeatTable.get("Mumbai", "IT");

        assertThat(seatCount).isEqualTo(60);
    }

    @Test
    public void givenArrayTable_whenGet_returnsSuccessfully() {
        final List<String> universityRowTable = Lists.newArrayList("Mumbai", "Harvard");
        final List<String> courseColumnTables = Lists.newArrayList("Chemical", "IT", "Electrical");
        final Table<String, String, Integer> universityCourseSeatTable = ArrayTable.create(universityRowTable, courseColumnTables);
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        final int seatCount = universityCourseSeatTable.get("Mumbai", "IT");

        assertThat(seatCount).isEqualTo(60);
    }
}
