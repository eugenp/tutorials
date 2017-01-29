package org.baeldung.guava;

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

public class GuavaTableTest {

    @Test
    public void givenTable_whenGet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final int seatCount = universityCourseSeatTable.get("Mumbai University", "IT");
        final Integer seatCountForNoEntry = universityCourseSeatTable.get("Oxford University", "IT");

        assertThat(seatCount).isEqualTo(60);
        assertThat(seatCountForNoEntry).isEqualTo(null);
    }

    @Test
    public void givenTable_whenContains_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final boolean entryIsPresent = universityCourseSeatTable.contains("Mumbai University", "IT");
        final boolean entryIsAbsent = universityCourseSeatTable.contains("Oxford University", "IT");
        final boolean courseIsPresent = universityCourseSeatTable.containsColumn("IT");
        final boolean universityIsPresent = universityCourseSeatTable.containsRow("Mumbai University");
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
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);

        final int seatCount = universityCourseSeatTable.remove("Mumbai University", "IT");

        assertThat(seatCount).isEqualTo(60);
        assertThat(universityCourseSeatTable.remove("Mumbai University", "IT")).isEqualTo(null);
    }

    @Test
    public void givenTable_whenColumn_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Map<String, Integer> universitySeatMap = universityCourseSeatTable.column("IT");

        assertThat(universitySeatMap).hasSize(2);
        assertThat(universitySeatMap.get("Mumbai University")).isEqualTo(60);
        assertThat(universitySeatMap.get("Harvard University")).isEqualTo(120);
    }

    @Test
    public void givenTable_whenColumnMap_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Map<String, Map<String, Integer>> courseKeyUniversitySeatMap = universityCourseSeatTable.columnMap();

        assertThat(courseKeyUniversitySeatMap).hasSize(3);
        assertThat(courseKeyUniversitySeatMap.get("IT")).hasSize(2);
        assertThat(courseKeyUniversitySeatMap.get("Electrical")).hasSize(1);
        assertThat(courseKeyUniversitySeatMap.get("Chemical")).hasSize(1);
    }

    @Test
    public void givenTable_whenRow_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Map<String, Integer> courseSeatMap = universityCourseSeatTable.row("Mumbai University");

        assertThat(courseSeatMap).hasSize(2);
        assertThat(courseSeatMap.get("IT")).isEqualTo(60);
        assertThat(courseSeatMap.get("Chemical")).isEqualTo(120);
    }

    @Test
    public void givenTable_whenRowKeySet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Set<String> universitySet = universityCourseSeatTable.rowKeySet();

        assertThat(universitySet).hasSize(2);
    }

    @Test
    public void givenTable_whenColKeySet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Set<String> courseSet = universityCourseSeatTable.columnKeySet();

        assertThat(courseSet).hasSize(3);
    }

    @Test
    public void givenTreeTable_whenGet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = TreeBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final int seatCount = universityCourseSeatTable.get("Mumbai University", "IT");

        assertThat(seatCount).isEqualTo(60);
    }

    @Test
    public void givenImmutableTable_whenGet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = ImmutableTable.<String, String, Integer> builder().put("Mumbai University", "Chemical", 120).put("Mumbai University", "IT", 60).put("Harvard University", "Electrical", 60)
                .put("Harvard University", "IT", 120).build();

        final int seatCount = universityCourseSeatTable.get("Mumbai University", "IT");

        assertThat(seatCount).isEqualTo(60);
    }

    @Test
    public void givenArrayTable_whenGet_returnsSuccessfully() {
        final List<String> universityRowTable = Lists.newArrayList("Mumbai University", "Harvard University");
        final List<String> courseColumnTables = Lists.newArrayList("Chemical", "IT", "Electrical");
        final Table<String, String, Integer> universityCourseSeatTable = ArrayTable.create(universityRowTable, courseColumnTables);
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final int seatCount = universityCourseSeatTable.get("Mumbai University", "IT");

        assertThat(seatCount).isEqualTo(60);
    }
}
