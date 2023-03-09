import org.example.StudentRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentRecordJUnitTest {

    @Test
    public void givenStudentRecordData_whenCreated_thenStudentPropertiesMatch() {
        StudentRecord s1 = new StudentRecord("John", 1, 90);
        StudentRecord s2 = new StudentRecord("Jane", 2, 80);
        assertEquals("John", s1.name());
        assertEquals(1, s1.rollNo());
        assertEquals(90, s1.marks());
        assertEquals("Jane", s2.name());
        assertEquals(2, s2.rollNo());
        assertEquals(80, s2.marks());
    }
    @Test
    public void givenStudentRecordsList_whenSortingDataWithName_thenStudentsSorted(){
        List<StudentRecord> studentRecords = List.of(
                new StudentRecord("Dana", 1, 85),
                new StudentRecord("Jim", 2, 90),
                new StudentRecord("Jane", 3, 80)
        );

        List<StudentRecord> mutableStudentRecords = new ArrayList<>(studentRecords);
        mutableStudentRecords.sort(Comparator.comparing(StudentRecord::name));

        List<StudentRecord> sortedStudentRecords = List.copyOf(mutableStudentRecords);
        assertEquals("Jane", sortedStudentRecords.get(1).name());
    }
}
