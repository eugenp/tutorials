package models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentStore {
    private static StudentStore instance;
    private Map<Integer, Student> students = new HashMap<>();

    public static StudentStore getInstance() {
        if (instance == null)
            instance = new StudentStore();
        return instance;
    }

    public Student addStudent(Student student) {
        int id = students.size() + 1;
        student.setId(id);
        students.put(id, student);
        return student;
    }

    public Student getStudent(int id) {
        return students.get(id);
    }

    public Set<Student> getAllStudents() {
        return (Set<Student>)students.values();
    }

    public Student updateStudent(Student student) {
		int id=student.getId();
        if (students.containsKey(id)) {
            students.put(id, student);
            return student;
        }
        return null;
    }

    public boolean deleteStudent(int id) {
        Student student=students.remove(id);
        if (student == null)
            return false;
		else
            return true;

    }
}