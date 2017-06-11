package com.baeldung.dependency.field;


import com.baeldung.dependency.domain.User;
import com.baeldung.dependency.repository.IUserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceFieldImplTest {


    @InjectMocks
    UserServiceFieldImpl userService = new UserServiceFieldImpl();

    @Mock
    IUserRepository userRepository;

    @Test
    public void createUser() throws Exception {

        User user = new User();
        user.setId(1);
        user.setName("John");

        when(userRepository.findById(1)).thenReturn(user);

        userService.createUser(user);
        User foundUser = userService.findById(1);

        assertThat(foundUser, is(not(equalTo(null))));
        assertThat(foundUser.getId(), is(equalTo(1)));
        assertThat(foundUser.getName(), is(equalTo("John")));

    }

}
