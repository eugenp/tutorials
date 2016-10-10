package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StudentStore {
    private static StudentStore instance;
    private Map<Integer, Student> students = new HashMap<>();

    public static StudentStore getInstance() {
        if (instance == null) {
            instance = new StudentStore();
        }
        return instance;
    }

    public Student addStudent(Student student) {
        int id = students.size();
        student.setId(id);
        students.put(id, student);
        return student;
    }

    public Student getStudent(int id) {
        return students.get(id);
    }

    public Set<Student> getAllStudents() {
<<<<<<< HEAD
        return (Set<Student>)students.values();
=======
        return new HashSet<>(students.values());
>>>>>>> e7d5f617adc2071f91af87cd7dc06dafe715eda1
    }

    public Student updateStudent(Student student) {
        int id = student.getId();
        if (students.containsKey(id)) {
            students.put(id, student);
            return student;
        }
        return null;
    }

    public boolean deleteStudent(int id) {
<<<<<<< HEAD
        Student student=students.remove(id);
        if (student == null)
            return false;
		else
            return true;

=======
        return students.remove(id) != null;
>>>>>>> e7d5f617adc2071f91af87cd7dc06dafe715eda1
    }
}