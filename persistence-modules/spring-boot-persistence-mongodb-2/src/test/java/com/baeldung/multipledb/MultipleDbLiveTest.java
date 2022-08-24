package com.baeldung.multipledb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.baeldung.multipledb.model.Account;
import com.baeldung.multipledb.model.User;
import com.baeldung.multipledb.repository.primary.UserRepository;
import com.baeldung.multipledb.repository.secondary.AccountRepository;

@SpringBootTest(classes = SpringBootMultipleDbApplication.class)
@TestPropertySource("/multipledb/multidb.properties")
public class MultipleDbLiveTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        accountRepository.deleteAll();

        dbSetup();
    }

    private void dbSetup() {
        User person = new User();
        person.setName("name");
        person.setSurname("surname");
        person.setEmail("user@gmail.com");
        person.setAge(50);

        User another_person = new User();
        another_person.setName("another_name");
        another_person.setSurname("another_surname");
        another_person.setEmail("another_user@hotmail.com");
        another_person.setAge(40);

        userRepository.saveAll(Arrays.asList(person, another_person));

        Account account = new Account();
        account.setUserEmail("user@gmail.com");
        account.setAccountDomain("account@jira.baeldung.com");
        account.setNickName("nickname");
        account.setPassword("password");

        Account another_account = new Account();
        another_account.setUserEmail("another_user@yahoo.com");
        another_account.setAccountDomain("another_account@slack.com");
        another_account.setNickName("another_nickname");
        another_account.setPassword("another_password");

        accountRepository.saveAll(Arrays.asList(account, another_account));
    }

    @Test
    void whenFindUserByEmail_thenNameOk() {
        assertEquals("name", userRepository.findByEmail("user@gmail.com")
          .getName());
    }

    @Test
    void whenFindAccountByDomain_thenNickNameOk() {
        assertEquals("nickname", accountRepository.findByAccountDomain("account@jira.baeldung.com")
          .getNickName());
    }
}
