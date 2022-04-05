package lifecycleevents;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.lifecycleevents.SpringBootLifecycleEventApplication;
import com.baeldung.lifecycleevents.model.User;
import com.baeldung.lifecycleevents.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootLifecycleEventApplication.class)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    
    @Before
    public void setup() {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setUserName("jsmith123");
        userRepository.save(user);
    }
    
    @After
    public void cleanup() {
        userRepository.deleteAll();
    }
    
    @Test
    public void whenNewUserProvided_userIsAdded() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("jdoe123");
        user = userRepository.save(user);
        assertTrue(user.getId() > 0);
    }
    
    @Test
    public void whenUserNameProvided_userIsLoaded() {
        User user = userRepository.findByUserName("jsmith123");
        assertNotNull(user);
        assertEquals("jsmith123", user.getUserName());
    }
    
    @Test
    public void whenExistingUserProvided_userIsUpdated() {
        User user = userRepository.findByUserName("jsmith123");
        user.setFirstName("Joe");
        user = userRepository.save(user);
        assertEquals("Joe", user.getFirstName());
    }
    
    @Test
    public void whenExistingUserDeleted_userIsDeleted() {
        User user = userRepository.findByUserName("jsmith123");
        userRepository.delete(user);
        user = userRepository.findByUserName("jsmith123");
        assertNull(user);
    }
    
    @Test
    public void whenExistingUserLoaded_fullNameIsAvailable() {
        String expectedFullName = "Jane Smith";
        User user = userRepository.findByUserName("jsmith123");
        assertEquals(expectedFullName, user.getFullName());
    }
}
