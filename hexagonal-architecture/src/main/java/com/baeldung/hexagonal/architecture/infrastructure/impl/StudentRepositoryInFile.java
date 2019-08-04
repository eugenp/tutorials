package com.baeldung.hexagonal.architecture.infrastructure.impl;

import com.baeldung.hexagonal.architecture.domain.Student;
import com.baeldung.hexagonal.architecture.domain.port.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Optional;

@Component
public class StudentRepositoryInFile implements StudentRepository {

  private final Logger logger = LoggerFactory.getLogger(StudentRepositoryInFile.class);

  @Override
  public Student saveStudent(Student student) {
    student.setId(System.nanoTime());
    String content2Write = student.getId() + " -- " + student.getName() + " -- " + student.getAge();
    content2Write += "\n";

    try (OutputStream os = new FileOutputStream("students.txt", true)) {
      os.write(content2Write.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      logger.error("error while saving student in file. {}", e);
      throw new RuntimeException(e);
    }

    return student;
  }

  @Override
  public Student getStudent(long id) {
    Student student = new Student();
    try {
      Optional<String> row =
          Files.lines(FileSystems.getDefault().getPath("students.txt"), StandardCharsets.UTF_8)
              .filter(line -> line.split(" -- ")[0].equalsIgnoreCase(String.valueOf(id)))
              .findFirst();

      if (row.isPresent()) {
        String rowAsArray[] = row.get().split(" -- ");
        student.setId(Long.parseLong(rowAsArray[0]));
        student.setName(rowAsArray[1]);
        student.setAge(Integer.parseInt(rowAsArray[2]));
      }
    } catch (IOException e) {
      logger.error("error while saving student in file. {}", e);
      throw new RuntimeException(e);
    }

    return student;
  }
}
