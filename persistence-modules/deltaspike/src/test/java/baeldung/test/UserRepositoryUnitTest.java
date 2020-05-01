package baeldung.test;

import baeldung.data.UserRepository;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by adam.
 */
@RunWith(CdiTestRunner.class)
public class UserRepositoryUnitTest {

    @Inject private UserRepository userRepository;

    @Test
    public void givenFourUsersWhenFindAllShouldReturnFourUsers() {
        assertThat(userRepository
          .findAll()
          .size(), equalTo(4));
    }

    @Test
    public void givenTwoUsersWithSpecifiedNameWhenFindByFirstNameShouldReturnTwoUsers() {
        assertThat(userRepository
          .findByFirstName("Adam")
          .size(), equalTo(2));
    }

    @Test
    public void givenTwoUsersWithSpecifiedNameWhenFindUsersWithFirstNameShouldReturnTwoUsers() {
        assertThat(userRepository
          .findUsersWithFirstName("Adam")
          .size(), equalTo(2));
    }

    @Test
    public void givenTwoUsersWithSpecifiedNameWhenFindUsersWithFirstNameNativeShouldReturnTwoUsers() {
        assertThat(userRepository
          .findUsersWithFirstNameNative("Adam")
          .size(), equalTo(2));
    }

    @Test
    public void givenTwoUsersWithSpecifiedLastNameWhenFindByLastNameShouldReturnTwoUsers() {
        assertThat(userRepository
          .findByLastName("LastName3")
          .size(), equalTo(2));
    }
}
