package baeldung.test;

import baeldung.data.SimpleUserRepository;
import baeldung.model.User;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by adam.
 */
@RunWith(CdiTestRunner.class)
public class SimpleUserRepositoryUnitTest {

    @Inject private EntityManager entityManager;

    @Inject private SimpleUserRepository simpleUserRepository;

    @Test
    public void givenFourUsersWhenFindAllShouldReturnFourUsers() {
        assertThat(simpleUserRepository
          .findAll()
          .size(), equalTo(4));
    }

    @Test
    public void givenTwoUsersWithSpecifiedNameWhenFindByFirstNameShouldReturnTwoUsers() {
        assertThat(simpleUserRepository
          .findByFirstName("Adam")
          .size(), equalTo(2));
    }

    @Test
    public void givenTwoUsersWithSpecifiedNameWhenFindAnyByFirstNameShouldReturnTwoUsers() {
        assertThat(simpleUserRepository
          .findAnyByFirstName("Adam")
          .size(), equalTo(2));
    }

    @Test
    public void givenTwoUsersWithSpecifiedNameWhenCountByFirstNameShouldReturnSizeTwo() {
        assertThat(simpleUserRepository.count(), equalTo(4));
    }

    @Test
    public void givenTwoUsersWithSpecifiedNameWhenRemoveByFirstNameShouldReturnSizeTwo() {
        simpleUserRepository.remove(entityManager.merge(simpleUserRepository.findById(1L)));
        assertThat(entityManager.find(User.class, 1L), nullValue());
    }

    @Test
    public void givenOneUserWithSpecifiedFirstNameAndLastNameWhenFindByFirstNameAndLastNameShouldReturnOneUser() {
        assertThat(simpleUserRepository
          .findByFirstNameAndLastName("Adam", "LastName1")
          .size(), equalTo(1));
        assertThat(simpleUserRepository
          .findByFirstNameAndLastName("David", "LastName2")
          .size(), equalTo(1));
    }

    @Test
    public void givenOneUserWithSpecifiedLastNameWhenFindAnyByLastNameShouldReturnOneUser() {
        assertThat(simpleUserRepository.findAnyByLastName("LastName1"), notNullValue());
    }

    @Test
    public void givenOneUserWithSpecifiedAddressCityWhenFindByCityShouldReturnOneUser() {
        assertThat(simpleUserRepository
          .findByAddress_city("London")
          .size(), equalTo(1));
    }

    @Test
    public void givenUsersWithSpecifiedFirstOrLastNameWhenFindByFirstNameOrLastNameShouldReturnTwoUsers() {
        assertThat(simpleUserRepository
          .findByFirstNameOrLastName("David", "LastName1")
          .size(), equalTo(2));
    }

    @Test
    public void givenUsersWhenFindAllOrderByFirstNameAscShouldReturnFirstAdamLastPeter() {
        List<User> users = simpleUserRepository.findAllOrderByFirstNameAsc();
        assertThat(users
          .get(0)
          .getFirstName(), equalTo("Adam"));
        assertThat(users
          .get(3)
          .getFirstName(), equalTo("Peter"));
    }

    @Test
    public void givenUsersWhenFindAllOrderByFirstNameAscLastNameDescShouldReturnFirstAdamLastPeter() {
        List<User> users = simpleUserRepository.findAllOrderByFirstNameAscLastNameDesc();
        assertThat(users
          .get(0)
          .getFirstName(), equalTo("Adam"));
        assertThat(users
          .get(3)
          .getFirstName(), equalTo("Peter"));
    }

    @Test
    public void givenUsersWhenFindTop2ShouldReturnTwoUsers() {
        assertThat(simpleUserRepository
          .findTop2OrderByFirstNameAsc()
          .size(), equalTo(2));
    }

    @Test
    public void givenUsersWhenFindFirst2ShouldReturnTwoUsers() {
        assertThat(simpleUserRepository
          .findFirst2OrderByFirstNameAsc()
          .size(), equalTo(2));
    }

    @Test
    public void givenPagesWithSizeTwoWhenFindAllOrderByFirstNameAscShouldReturnTwoPages() {
        assertThat(simpleUserRepository
          .findAllOrderByFirstNameAsc(0, 2)
          .size(), equalTo(2));
        assertThat(simpleUserRepository
          .findAllOrderByFirstNameAsc(2, 4)
          .size(), equalTo(2));
    }

}
