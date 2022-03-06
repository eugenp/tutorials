package unit;

import com.baeldung.domain.User;
import com.baeldung.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserUnitTest {

    @Test
    public void IsObjectReturnedWhenSaved(){
        User user= new User();
        user.setName("Adeola");
        user.setAge(28);
        UserRepository userRepository= new UserRepository();
       User result= userRepository.createUserRepo(user);
        assertEquals("Adeola",result.getName());
    }

    @Test
    public void IsUsersRecordReturned(){
        UserRepository userRepository= new UserRepository();
        List<User> users = userRepository.getUserRepo();
        assertEquals(1,users.size());
    }



}

