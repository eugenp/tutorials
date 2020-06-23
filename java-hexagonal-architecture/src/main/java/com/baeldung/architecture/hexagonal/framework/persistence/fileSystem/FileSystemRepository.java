package com.baeldung.architecture.hexagonal.framework.persistence.fileSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.baeldung.architecture.hexagonal.domain.Student;

@Component
public class FileSystemRepository {

    private static String FS_DIR = "target/students/";

    public Student save(Student student) {
        Long studentId = null;
        File file = new File(FS_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (student.getId() != null) {
            studentId = student.getId();
        } else {
            studentId = Math.abs(new Random().nextLong());
            student.setId(studentId);
        }
        file = new File(FS_DIR + studentId);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        saveDataToFile(student, studentId, file);
        return student;
    }

    public void deleteById(Long id) {
        File file = new File(FS_DIR + id);
        if (file.exists()) {
            file.delete();
        }
    }

    public Optional<Student> findById(Long id) {
        String studentData = null;
        Student student = null;
        File file = new File(FS_DIR + id);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String contents = null;
                while ((contents = br.readLine()) != null) {
                    studentData = studentData + contents;
                }
            } catch (IOException e) {
            }
            String[] sArray = studentData.split(",");
            student = new Student(sArray[1], Float.valueOf(sArray[2]));
            student.setId(id);
        }
        return Optional.of(student);
    }

    private void saveDataToFile(Student student, Long studentId, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(studentId + "," + student.getName() + "," + student.getScore());
            if (student.getPerformanceRating() != null)
                bw.write("," + student.getPerformanceRating());
        } catch (IOException e) {
        }
    }
}
