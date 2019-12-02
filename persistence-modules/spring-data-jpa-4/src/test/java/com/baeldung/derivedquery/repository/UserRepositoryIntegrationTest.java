package com.baeldung.derivedquery.repository;

import com.baeldung.Application;
import com.baeldung.derivedquery.entity.User;
import com.baeldung.derivedquery.repository.UserRepository;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryIntegrationTest {

    private static final String USER_NAME_ADAM = "Adam";
    private static final String USER_NAME_EVE = "Eve";
    private static final ZonedDateTime BIRTHDATE = ZonedDateTime.now();

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {

        User user1 = new User(USER_NAME_ADAM, 25, BIRTHDATE, true);
        User user2 = new User(USER_NAME_ADAM, 20, BIRTHDATE, false);
        User user3 = new User(USER_NAME_EVE, 20, BIRTHDATE, true);
        User user4 = new User(null, 30, BIRTHDATE, false);

        userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));
    }

    @After
    public void tearDown() {

        userRepository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnsCorrectResult() {

        assertEquals(2, userRepository.findByName(USER_NAME_ADAM).size());
    }

    @Test
    public void whenFindByNameIsNull_thenReturnsCorrectResult() {

        assertEquals(1, userRepository.findByNameIsNull().size());
    }

    @Test
    public void whenFindByNameNot_thenReturnsCorrectResult() {

        assertEquals(USER_NAME_EVE, userRepository.findByNameNot(USER_NAME_ADAM).get(0).getName());
    }

    @Test
    public void whenFindByNameStartingWith_thenReturnsCorrectResult() {

        assertEquals(2, userRepository.findByNameStartingWith("A").size());
    }

    @Test
    public void whenFindByNameEndingWith_thenReturnsCorrectResult() {

        assertEquals(1, userRepository.findByNameEndingWith("e").size());
    }

    @Test
    public void whenByNameContaining_thenReturnsCorrectResult() {

        assertEquals(1, userRepository.findByNameContaining("v").size());
    }


    @Test
    public void whenByNameLike_thenReturnsCorrectResult() {

        assertEquals(2, userRepository.findByNameEndingWith("m").size());
    }

    @Test
    public void whenByAgeLessThan_thenReturnsCorrectResult() {

        assertEquals(2, userRepository.findByAgeLessThan(25).size());
    }


    @Test
    public void whenByAgeLessThanEqual_thenReturnsCorrectResult() {

        assertEquals(3, userRepository.findByAgeLessThanEqual(25).size());
    }

    @Test
    public void whenByAgeGreaterThan_thenReturnsCorrectResult() {

        assertEquals(1, userRepository.findByAgeGreaterThan(25).size());
    }

    @Test
    public void whenByAgeGreaterThanEqual_thenReturnsCorrectResult() {

        assertEquals(2, userRepository.findByAgeGreaterThanEqual(25).size());
    }

    @Test
    public void whenByAgeBetween_thenReturnsCorrectResult() {

        assertEquals(4, userRepository.findByAgeBetween(20, 30).size());
    }

    @Test
    public void whenByBirthDateAfter_thenReturnsCorrectResult() {

        final ZonedDateTime yesterday = BIRTHDATE.minusDays(1);
        assertEquals(4, userRepository.findByBirthDateAfter(yesterday).size());
    }

    @Test
    public void whenByBirthDateBefore_thenReturnsCorrectResult() {

        final ZonedDateTime yesterday = BIRTHDATE.minusDays(1);
        assertEquals(0, userRepository.findByBirthDateBefore(yesterday).size());
    }

    @Test
    public void whenByActiveTrue_thenReturnsCorrectResult() {

        assertEquals(2, userRepository.findByActiveTrue().size());
    }

    @Test
    public void whenByActiveFalse_thenReturnsCorrectResult() {

        assertEquals(2, userRepository.findByActiveFalse().size());
    }


    @Test
    public void whenByAgeIn_thenReturnsCorrectResult() {

        final List<Integer> ages = Arrays.asList(20, 25);
        assertEquals(3, userRepository.findByAgeIn(ages).size());
    }

    @Test
    public void whenByNameOrBirthDate() {

        assertEquals(4, userRepository.findByNameOrBirthDate(USER_NAME_ADAM, BIRTHDATE).size());
    }

    @Test
    public void whenByNameOrBirthDateAndActive() {

        assertEquals(3, userRepository.findByNameOrBirthDateAndActive(USER_NAME_ADAM, BIRTHDATE, false).size());
    }

    @Test
    public void whenByNameOrderByName() {

        assertEquals(2, userRepository.findByNameOrderByName(USER_NAME_ADAM).size());
    }
}