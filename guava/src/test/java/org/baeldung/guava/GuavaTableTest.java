package org.baeldung.guava;

import static org.junit.Assert.*;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

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

        assertEquals(60, seatCount);
        assertNull(seatCountForNoEntry);
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

        assertTrue(entryIsPresent);
        assertFalse(entryIsAbsent);
        assertTrue(courseIsPresent);
        assertTrue(universityIsPresent);
        assertTrue(seatCountIsPresent);
    }

    @Test
    public void givenTable_whenRemove_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);

        final int seatCount = universityCourseSeatTable.remove("Mumbai University", "IT");

        assertEquals(60, seatCount);
        assertNull(universityCourseSeatTable.remove("Mumbai University", "IT"));
    }

    @Test
    public void givenTable_whenColumn_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Map<String, Integer> universitySeatMap = universityCourseSeatTable.column("IT");

        assertEquals(2, universitySeatMap.size());
        assertEquals(60, universitySeatMap.get("Mumbai University").intValue());
        assertEquals(120, universitySeatMap.get("Harvard University").intValue());
    }

    @Test
    public void givenTable_whenColumnMap_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Map<String, Map<String, Integer>> courseKeyUniversitySeatMap = universityCourseSeatTable.columnMap();

        assertEquals(3, courseKeyUniversitySeatMap.size());
        assertEquals(2, courseKeyUniversitySeatMap.get("IT").size());
        assertEquals(1, courseKeyUniversitySeatMap.get("Electrical").size());
        assertEquals(1, courseKeyUniversitySeatMap.get("Chemical").size());
    }

    @Test
    public void givenTable_whenRow_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Map<String, Integer> courseSeatMap = universityCourseSeatTable.row("Mumbai University");

        assertEquals(2, courseSeatMap.size());
        assertEquals(60, courseSeatMap.get("IT").intValue());
        assertEquals(120, courseSeatMap.get("Chemical").intValue());
    }

    @Test
    public void givenTable_whenRowKeySet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Set<String> universitySet = universityCourseSeatTable.rowKeySet();

        assertEquals(2, universitySet.size());
    }

    @Test
    public void givenTable_whenColKeySet_returnsSuccessfully() {
        final Table<String, String, Integer> universityCourseSeatTable = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai University", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai University", "IT", 60);
        universityCourseSeatTable.put("Harvard University", "Electrical", 60);
        universityCourseSeatTable.put("Harvard University", "IT", 120);

        final Set<String> courseSet = universityCourseSeatTable.columnKeySet();

        assertEquals(3, courseSet.size());
    }
}
