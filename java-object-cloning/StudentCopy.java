import java.util.ArrayList;
import java.util.List;

public class StudentCopy {
    public static void main(String[] args){

        // Creating courses list
        List<String> originalCourses = new ArrayList<>();
        originalCourses.add("Java");
        originalCourses.add("PHP");

        //creating original student
        Student originalStudent = new Student("Sara", originalCourses);

        // Shallow copy
        Student shallowCopyStudent = originalStudent.shallowCopy();

        // Deep copy
        Student deepCopyStudent = originalStudent.deepCopy();

        // Modifying the courses list in the original student
        originalCourses.add("MySQL");

        // Displaying information
        System.out.println("Original Student: " + originalStudent);
        System.out.println("Shallow Copy Student: " + shallowCopyStudent);
        System.out.println("Deep Copy Student: " + deepCopyStudent);

    }
}
