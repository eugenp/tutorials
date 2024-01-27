package com.baeldung.boot.findby;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.findby.Account;
import com.baeldung.findby.AccountApplication;
import com.baeldung.findby.AccountRepository;
import com.baeldung.findby.Permission;
import com.baeldung.findby.PermissionRepository;

@SpringBootTest(classes = AccountApplication.class)
public class AccountRepositoryUnitTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @BeforeEach
    public void setup() {
        saveAccount();
    }

    @AfterEach
    public void tearDown() {
        accountRepository.deleteAll();
        permissionRepository.deleteAll();
    }

    @Test
    public void givenAccountInDb_whenPerformFindByEmail_thenReturnsAccount() {
        String email = "test@test.com";
        Account account = accountRepository.findByEmail(email);
        assertThat(account.getEmail()).isEqualTo(email);
    }

    @Test
    public void givenAccountInDb_whenPerformFindByUsernameAndEmail_thenReturnsAccount() {
        String email = "test@test.com";
        String username = "user_admin";
        Account account = accountRepository.findByUsernameAndEmail(username, email);
        assertThat(account.getUsername()).isEqualTo(username);
        assertThat(account.getEmail()).isEqualTo(email);
    }

    @Test
    public void givenAccountInDb_whenPerformFindByUsernameOrEmail_thenReturnsAccount() {
        String email = "test@test.com";
        String username = "user_editor";
        Account account = accountRepository.findByUsernameOrEmail(username, email);
        assertThat(account.getUsername()).isNotEqualTo(username);
        assertThat(account.getEmail()).isEqualTo(email);
    }

    @Test
    public void givenAccountInDb_whenPerformFindByUsernameInOrEmailIn_thenReturnsAccounts() {
        List<String> emails = Arrays.asList("test@test.com", "abc@abc.com", "pqr@pqr.com");
        List<String> usernames = Arrays.asList("user_editor", "user_admin");
        List<Account> byUsernameInOrEmailIn = accountRepository.findByUsernameInOrEmailIn(usernames, emails);
        assertThat(byUsernameInOrEmailIn.size()).isEqualTo(1);
        assertThat(byUsernameInOrEmailIn.get(0)
            .getEmail()).isEqualTo("test@test.com");
    }

    private Permission getPermissions() {
        Permission editor = new Permission();
        editor.setType("editor");
        permissionRepository.save(editor);
        return editor;
    }

    private void saveAccount() {
        Account account = getAccount();
        accountRepository.save(account);
    }

    private Account getAccount() {
        Account account = new Account();
        account.setEmail("test@test.com");
        account.setUsername("user_admin");
        account.setPermission(getPermissions());
        account.setPassword(UUID.randomUUID()
            .toString());
        account.setCreatedOn(Timestamp.from(Instant.now()));
        account.setLastLogin(Timestamp.from(Instant.now()));
        return account;
    }
}
