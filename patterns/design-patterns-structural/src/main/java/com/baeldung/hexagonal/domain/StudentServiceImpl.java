package com.baeldung.hexagonal.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.port.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
    
    // DB repository mock
    private Map<Long, Student> repository = Arrays.asList(
        new Student[]{
                new Student(1, "Alan","Turing"),
                new Student(2, "Sebastian","Bach"),
                new Student(3, "Pablo","Picasso"),
        }).stream()
        .collect(Collectors.toConcurrentMap(s -> s.getId(), Function.identity()));
    
    // DB id sequence mock
    private AtomicLong sequence = new AtomicLong(3);
    
    @Override
    public List<Student> readAll() {
        return repository.values().stream().collect(Collectors.toList());
    }
    
    @Override
    public Student read(Long id) {
        return repository.get(id);
    }

    @Override
    public Student create(Student student) {
        long key = sequence.incrementAndGet();
        student.setId(key);
        repository.put(key, student);
        return student;
    }
    
    @Override
    public Student update(Long id, Student student) {
        student.setId(id);
        Student oldStudent = repository.replace(id, student);
        return oldStudent == null ? null : student;
    }
    
    @Override
    public void delete(Long id) {
        repository.remove(id);
    }
}
