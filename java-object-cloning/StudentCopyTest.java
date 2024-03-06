import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class StudentCopyTest {
    @Test
    public void testCopy() {

        // Creating coursess list
        List<String> originalCourses = new ArrayList<>();
        originalCourses.add("C/C++");
        originalCourses.add("PHP");

        //creating original student
        Student originalStudent = new Student("Somia", originalCourses);

        // Shallow copy
        Student shallowCopyStudent = originalStudent.shallowCopy();

        // Deep copy
        Student deepCopyStudent = originalStudent.deepCopy();

        // Modifying the courses list in the original student
        originalCourses.add("DSA");

        // Assertions
        assertEquals(originalStudent.toString(), shallowCopyStudent.toString());
        assertNotEquals(originalStudent.toString(), deepCopyStudent.toString());
        assertEquals(shallowCopyStudent.getCourses(), originalStudent.getCourses());
        assertNotEquals(deepCopyStudent.getCourses(), originalStudent.getCourses());
    }
}
