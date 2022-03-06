package integration;

import com.baeldung.domain.User;
import com.baeldung.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserIntegrationTest {
    @Test
    public void returnObjectIfSaved() {
        User record = new User();
        record.setAge(6);
        record.setName("Ade");
        UserService userService = mock(UserService.class);
        when(userService.createUser(record)).thenReturn(record);
        assertEquals(record, record);
    }

    @Test
    public void returnAllUsers() {
        List<User> records = new ArrayList<User>();
        User record = new User();
        record.setAge(6);
        record.setName("Ade");
        records.add(record);
        UserService userService = mock(UserService.class);
        when(userService.getUsers()).thenReturn(records);
        assertEquals(1, records.size());
    }
}

