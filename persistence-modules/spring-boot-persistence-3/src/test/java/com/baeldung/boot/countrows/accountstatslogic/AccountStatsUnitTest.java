package com.baeldung.boot.countrows.accountstatslogic;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.baeldung.countrows.AccountStatsApplication;
import com.baeldung.countrows.entity.Account;
import com.baeldung.countrows.entity.Permission;
import com.baeldung.countrows.repository.AccountRepository;
import com.baeldung.countrows.repository.PermissionRepository;
import com.baeldung.countrows.service.AccountStatsLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AccountStatsApplication.class)
class AccountStatsUnitTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountStatsLogic accountStatsLogic;

    @AfterEach
    public void afterEach() {
        accountRepository.deleteAll();
        permissionRepository.deleteAll();
    }

    @Test
    public void givenAccountInTable_whenPerformCount_returnsAppropriateCount() {
        savePermissions();
        saveAccount();
        assertThat(accountStatsLogic.getAccountCount()).isEqualTo(1);
    }

    @Test
    public void givenAccountInTable_whenPerformCountByUsernameOrPermission_returnsAppropriateCount() {
        savePermissions();
        Account account = saveAccount();
        assertThat(accountStatsLogic.getAccountCountByUsername(account.getUsername())).isEqualTo(1);
        assertThat(accountStatsLogic.getAccountCountByPermission(account.getPermission())).isEqualTo(1);
    }

    @Test
    public void givenAccountInTable_whenPerformCountByPermissionAndCreatedOn_returnsAppropriateCount() throws ParseException {
        savePermissions();
        Account account = saveAccount();
        long count = accountStatsLogic.getAccountCountByPermissionAndCreatedOn(account.getPermission(), account.getCreatedOn());
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAccountInTable_whenPerformCountUsingCQ_returnsAppropriateCount() throws ParseException {
        savePermissions();
        saveAccount();
        long count = accountStatsLogic.getAccountsUsingCQ();
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAccountInTable_whenPerformCountByPermissionUsingCQ_returnsAppropriateCount() throws ParseException {
        savePermissions();
        Account account = saveAccount();
        long count = accountStatsLogic.getAccountsByPermissionUsingCQ(account.getPermission());
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAccountInTable_whenPerformCountByPermissionAndCreatedOnUsingCQ_returnsAppropriateCount() throws ParseException {
        savePermissions();
        Account account = saveAccount();
        long count = accountStatsLogic.getAccountsByPermissionAndCreateOnUsingCQ(account.getPermission(), account.getCreatedOn());
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAccountInTable_whenPerformCountUsingJPQL_returnsAppropriateCount() throws ParseException {
        savePermissions();
        saveAccount();
        long count = accountStatsLogic.getAccountsUsingJPQL();
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAccountInTable_whenPerformCountByPermissionUsingJPQL_returnsAppropriateCount() throws ParseException {
        savePermissions();
        Account account = saveAccount();
        long count = accountStatsLogic.getAccountsByPermissionUsingJPQL(account.getPermission());
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void givenAccountInTable_whenPerformCountByPermissionAndCreatedOnUsingJPQL_returnsAppropriateCount() throws ParseException {
        savePermissions();
        Account account = saveAccount();
        long count = accountStatsLogic.getAccountsByPermissionAndCreatedOnUsingJPQL(account.getPermission(), account.getCreatedOn());
        assertThat(count).isEqualTo(1);
    }

    private Account saveAccount() {
        return accountRepository.save(getAccount());
    }

    private void savePermissions() {
        Permission editor = new Permission();
        editor.setType("editor");
        permissionRepository.save(editor);

        Permission admin = new Permission();
        admin.setType("admin");
        permissionRepository.save(admin);
    }

    private Account getAccount() {
        Permission permission = permissionRepository.findByType("admin");
        Account account = new Account();
        String seed = UUID.randomUUID()
            .toString();
        account.setUsername("username_" + seed);
        account.setEmail("username_" + seed + "@gmail.com");
        account.setPermission(permission);
        account.setPassword("password_q1234");
        account.setCreatedOn(Timestamp.from(Instant.now()));
        account.setLastLogin(Timestamp.from(Instant.now()));
        return account;
    }
}
