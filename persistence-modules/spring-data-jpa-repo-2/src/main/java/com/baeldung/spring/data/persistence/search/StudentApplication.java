package com.baeldung.spring.data.persistence.search;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class StudentApplication {

	private static final Logger log = LoggerFactory.getLogger(StudentApplication.class);

    @Autowired
    private StudentRepository studentRepo;

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void checkFinderMethods() {

        int count = 15;
        Random r = new Random();

        
        for (int i = 0; i < count; i++) {
        	        	
        	int score = r.nextInt(101);
        	Student s = new Student("Student-" + i, score);
            studentRepo.save(s);
        }
        
        List<Student> students = studentRepo.findAll();
        for(Student s : students) {
        	log.debug("{}", s);
        }
        		
        
        log.debug("\nExecuting findFirstByOrderByScore()");
       
        Student student = studentRepo.findFirstByOrderByScoreDesc();
      
        log.debug("{}", student);
        
        student = studentRepo.findTopByOrderByScoreDesc();
        log.debug("{}", student);
        
        List<Student> firstThree = studentRepo.findFirst3ByOrderByScoreDesc();
        log.debug("First 3");
        for(Student s : firstThree) {
        	log.debug("{}", s);
        }
        
        List<Student> topThree = studentRepo.findTop3ByOrderByScoreDesc();
        log.debug("Top 3");
        for(Student s : topThree) {
        	log.debug("{}", s);
        }
        
        log.debug("Getting the first student using Sort parameter");
        Student student2 = studentRepo.findFirstBy(Sort.by("score").descending());
        log.debug("{}", student2);
        
        log.debug("Calling findFirstByNameLike");
        student2 = studentRepo.findFirstByNameLike("%3%", Sort.by("score").descending());
        log.debug("{}", student2);
        
        int scoreThreshold = 75;
        List<Student> topTwoGreaterThanThreshold = studentRepo.findFirst2ByScoreGreaterThan(scoreThreshold, Sort.by("score").descending());
        log.debug("findFirst2ByScoreGreaterThan {}", scoreThreshold);
        for(Student s : topTwoGreaterThanThreshold) {
        	log.debug("{}", s);
        }
        
        
        List<Student> topTwoLessThanThreshold = studentRepo.findFirst2ByScoreLessThan(scoreThreshold, Sort.by("score").descending());
        log.debug("findFirst2ByScoreLessThan {}", scoreThreshold);
        for(Student s : topTwoLessThanThreshold) {
        	log.debug("{}", s);
        }
        
        List<Student> topTwoBetweenRange = studentRepo.findFirst2ByScoreBetween(50, 60, Sort.by("score").descending());
        log.debug("findFirst2ByScoreBetween {}", 50, 60);
        for(Student s : topTwoBetweenRange) {
        	log.debug("{}", s);
        }
    }
}
