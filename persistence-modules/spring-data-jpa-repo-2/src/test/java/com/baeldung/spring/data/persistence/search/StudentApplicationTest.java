package com.baeldung.spring.data.persistence.search;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentApplicationTest {
	private static final Logger log = LoggerFactory.getLogger(StudentApplicationTest.class);
	
	@Autowired
	StudentRepository studentRepo;
	
	private List<Student> students;
	
	@Before
	public void fillData() {
		students = new ArrayList<>();
		int count = 10;
        Random r = new Random();

        
        for (int i = 0; i < count; i++) {
        	        	
        	int score = r.nextInt(101);
        	Student s = new Student("Student-" + i, score);
            
            students.add(s);
        }
        
        studentRepo.saveAll(students);
        
//        List<Student> _students = studentRepo.findAll();
//        printData(_students);
       
        Comparator<Student> c = Comparator.comparing(a -> a.getScore());
		c = c.reversed();
		students.sort(c);
//		printData(students);
	}
	
	private void printData(List<Student> students) {
		for(Student s : students) {
        	log.debug("{}", s);
        }
	}
	
	@After
	public void clearData() {
		studentRepo.deleteAll();
	}

	@Test
    public void givenStudentScoresThenFindTopOne() {

		log.debug("\nExecuting findFirstByOrderByScore()");
        Student student = studentRepo.findFirstByOrderByScoreDesc();        
        log.debug("{}", student);
        
        Student s = students.get(0);
        
        assertEquals(student, s);
	}
	
	@Test
    public void givenStudentScoresThenFindTopThree() {

		List<Student> firstThree = studentRepo.findFirst3ByOrderByScoreDesc();
        log.debug("First 3");
        for(Student s : firstThree) {
        	log.debug("{}", s);
        }
        
        List<Student> sList = students.subList(0, 3);
        assertArrayEquals(firstThree.toArray(), sList.toArray());
	}
	
	@Test
    public void givenStudentScoresThenFindTopOneWhenNameMatches() {
		
		String matchString = "3";

		log.debug("Calling findFirstByNameLike");
        Student student = studentRepo.findFirstByNameLike("%" + matchString + "%", Sort.by("score").descending());
        log.debug("{}", student);
        
        Student s = students.stream().filter(a -> a.getName().contains(matchString)).findFirst().orElse(null);
        
        assertEquals(student, s);
	}
	
	@Test
    public void givenStudentScoresThenFindTop2WhenScoresBetweenRange() {
		
		List<Student> topTwoBetweenRange = studentRepo.findFirst2ByScoreBetween(50, 60, Sort.by("score").descending());
        log.debug("findFirst2ByScoreBetween {}", 50, 60);
        for(Student s : topTwoBetweenRange) {
        	log.debug("{}", s);
        }
        
        List<Student> _students = students.stream().filter(a -> a.getScore() >= 50 && a.getScore() <= 60).limit(2).collect(Collectors.toList());
        log.debug("Empty {}", topTwoBetweenRange.isEmpty());
        assertArrayEquals(_students.toArray(), topTwoBetweenRange.toArray());
	}
}
