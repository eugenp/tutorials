package store;

import model.Student;

import java.util.*;

public class StudentStore {
    private Map<Integer, Student> students = new HashMap<>();

    public Optional<Student> addStudent(Student student) {
        int id = students.size();
        student.setId(id);
        students.put(id, student);
        return Optional.ofNullable(student);
    }

    public Optional<Student> getStudent(int id) {
        return Optional.ofNullable(students.get(id));
    }

    public Set<Student> getAllStudents() {
        return new HashSet<>(students.values());
    }

    public Optional<Student> updateStudent(Student student) {
        int id = student.getId();
        if (students.containsKey(id)) {
            students.put(id, student);
            return Optional.ofNullable(student);
        }
        return Optional.empty();
    }

    public boolean deleteStudent(int id) {
        return students.remove(id) != null;
    }
}
