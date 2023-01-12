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
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentApplicationUnitTest {

    @Autowired
    private StudentRepository studentRepo;
    private List<Student> students;

    @Before
    public void fillData() {
        students = new ArrayList<>();
        int count = 10;
        Random r = new Random();
        List<Integer> scores = r.ints(0, 101)
          .distinct()
          .limit(count)
          .boxed()
          .collect(Collectors.toList());

        for (int i = 0; i < count; i++) {
            Integer score = scores.get(i);
            Student s = new Student("Student-" + i, score);
            students.add(s);
        }

        studentRepo.saveAll(students);
        Comparator<Student> c = Comparator.comparing(a -> a.getScore());
        c = c.reversed();
        students.sort(c);
    }

    @After
    public void clearData() {
        studentRepo.deleteAll();
    }

    @Test
    public void givenStudentScores_whenMoreThanOne_thenFindFirst() {

        Student student = studentRepo.findFirstByOrderByScoreDesc();
        Student s = students.get(0);
        assertEquals(student, s);
    }

    @Test
    public void givenStudentScores_whenMoreThan3_thenFindFirstThree() {

        List<Student> firstThree = studentRepo.findFirst3ByOrderByScoreDesc();
        List<Student> sList = students.subList(0, 3);
        assertArrayEquals(firstThree.toArray(), sList.toArray());
    }

    @Test
    public void givenStudentScores_whenNameMatches_thenFindFirstStudent() {

        String matchString = "3";
        Student student = studentRepo.findFirstByNameLike("%" + matchString + "%", Sort.by("score")
          .descending());
        Student s = students.stream()
          .filter(a -> a.getName()
          .contains(matchString))
          .findFirst()
          .orElse(null);
        assertEquals(student, s);
    }

    @Test
    public void givenStudentScores_whenBetweenRange_thenFindFirstTwoStudents() {

        List<Student> topTwoBetweenRange = studentRepo.findFirst2ByScoreBetween(50, 60, Sort.by("score")
          .descending());
        List<Student> _students = students.stream()
          .filter(a -> a.getScore() >= 50 && a.getScore() <= 60)
          .limit(2)
          .collect(Collectors.toList());
        assertArrayEquals(_students.toArray(), topTwoBetweenRange.toArray());
    }
}
