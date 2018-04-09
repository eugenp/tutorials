package com.example.webfluxsample.controller;

import java.time.Duration;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webfluxsample.model.Student;
import com.example.webfluxsample.repository.StudentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping(value = "/students")
    @ResponseBody
    public Flux<Student> findAll() {
        return studentRepository.findAll();
    }

    // Students are Sent to the client as Server Sent Events each 2s
    @GetMapping(value = "/stream/students", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Student> streamAll() {
        return studentRepository.findAll()
            .delayElements(Duration.ofMillis(2000));
    }

    @PostMapping("/students")
    @ResponseBody
    public Mono<Student> create(@Valid @RequestBody Student Student) {
        return studentRepository.save(Student);
    }

    @GetMapping("/students/{id}")
    @ResponseBody
    public Mono<ResponseEntity<Student>> getById(@PathVariable(value = "id") String StudentId) {
        return studentRepository.findById(StudentId)
            .map(savedStudent -> ResponseEntity.ok(savedStudent))
            .defaultIfEmpty(ResponseEntity.notFound()
                .build());
    }

    @PutMapping("/students/{id}")
    public Mono<ResponseEntity<Student>> update(@PathVariable(value = "id") String studentId, @Valid @RequestBody Student student) {
        return studentRepository.findById(studentId)
            .flatMap(existingStudent -> {
                existingStudent.setFullName(student.getFullName());
                existingStudent.setAddress(student.getAddress());
                return studentRepository.save(existingStudent);
            })
            .map(updatedStudent -> new ResponseEntity<>(updatedStudent, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/Students/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable(value = "id") String studentId) {

        return studentRepository.findById(studentId)
            .flatMap(existingStudent -> studentRepository.delete(existingStudent)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    Mono<String> homePage() {
        return Mono.just("home");
    }

}
