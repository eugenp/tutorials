package com.springWebflux.SpringwebfluxeventStreaming.controller;

import com.springWebflux.SpringwebfluxeventStreaming.model.Student;
import com.springWebflux.SpringwebfluxeventStreaming.model.StudentDataEvent;
import com.springWebflux.SpringwebfluxeventStreaming.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
    private StudentRepository studrepo;

    public StudentController(StudentRepository studrepo) {
        this.studrepo = studrepo;
    }

	    @GetMapping("/all")
	    public Flux<Student> getAllStudent() {
	        return studrepo
	                .findAll();
    }
	    
	      	    
    @GetMapping("/{id}")
    public Mono<Student> getByStudentId(@PathVariable("id") final int id) {
        return studrepo.findById(id);
    }

    @PostMapping("/saveStudent")
    public Mono<Student> saveStudent(@RequestBody Student student) {
        return studrepo
                .save(student);
}
    
@GetMapping("/delete/{id}")
public Mono<Void> deleteByStudentId(@PathVariable("id") final int id) {
    return studrepo.findById(id).flatMap(i -> studrepo.delete(i));
}

    @GetMapping(value = "/{id}/events", 
    		produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    		    public Flux<StudentDataEvent> 
    		    getEventsData(
    		   @PathVariable("id") final int stdId) {
    		        return studrepo.findById(stdId)
    		               .flatMapMany(e -> {
    		                Flux<Long> interval
    		                = Flux.interval(Duration.ofSeconds(1));
    		                Flux<StudentDataEvent> studentEventFlux 
    		                = Flux.fromStream(
    		                  Stream.generate(() -> 
    		                  new StudentDataEvent(e,new Date())));

                    return Flux.zip(interval, studentEventFlux)
                            .map(Tuple2::getT2);

                });

    }



}
