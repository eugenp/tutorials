package com.baeldung.architecture.hexagonal.framework;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.architecture.hexagonal.domain.Student;
import com.baeldung.architecture.hexagonal.domain.repository.StudentRepository;
import com.baeldung.architecture.hexagonal.framework.persistence.fileSystem.FileSystemRepository;

@Component(value = "fileAdapter")
public class FileAdapterForPersistence implements StudentRepository {

    @Autowired
    private FileSystemRepository fileSystemRepository;

    @Override
    public Student add(Student student) {
        return fileSystemRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        return fileSystemRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        fileSystemRepository.deleteById(id);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return fileSystemRepository.findById(id);
    }

}
