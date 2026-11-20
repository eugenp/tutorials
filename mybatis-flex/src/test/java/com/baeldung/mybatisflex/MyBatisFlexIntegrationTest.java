package com.baeldung.mybatisflex;

import static com.mybatisflex.core.query.QueryMethods.column;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.mybatisflex.entity.Account;
import com.baeldung.mybatisflex.mapper.AccountMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;

@SpringBootTest
@Transactional
public class MyBatisFlexIntegrationTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void whenInsertAndSelectById_thenAccountIsPersisted() {
        Account account = new Account();
        account.setUserName("olivia");
        account.setAge(28);
        account.setStatus("ACTIVE");
        account.setCreatedAt(LocalDateTime.of(2024, 5, 1, 12, 0));

        accountMapper.insert(account);
        Account persistedAccount = accountMapper.selectOneById(account.getId());

        assertNotNull(account.getId());
        assertNotNull(persistedAccount);
        assertEquals("olivia", persistedAccount.getUserName());
    }

    @Test
    public void whenUpdatingAnAccount_thenTheNewStatusIsStored() {
        Account account = accountMapper.selectOneById(1L);
        account.setStatus("INACTIVE");

        accountMapper.update(account);

        Account updatedAccount = accountMapper.selectOneById(1L);

        assertEquals("INACTIVE", updatedAccount.getStatus());
    }

    @Test
    public void whenDeleteById_thenAccountIsRemoved() {
        accountMapper.deleteById(2L);
        Account deletedAccount = accountMapper.selectOneById(2L);

        assertNull(deletedAccount);
    }

    @Test
    public void whenQueryWithFilters_thenMatchingAccountsAreReturned() {
        QueryWrapper queryWrapper = QueryWrapper.create()
          .where(Account::getAge).ge(18)
          .and(Account::getStatus).eq("ACTIVE")
          .orderBy(column("age").desc());

        List<Account> accounts = accountMapper.selectListByQuery(queryWrapper);

        assertEquals(3, accounts.size());
        assertEquals("emma", accounts.get(0).getUserName());
        assertEquals("sarah", accounts.get(1).getUserName());
        assertEquals("tom", accounts.get(2).getUserName());
    }

    @Test
    public void whenBuildingADynamicQuery_thenOnlyActiveAdultAccountsAreReturned() {
        Integer minAge = 18;
        String status = "ACTIVE";

        QueryWrapper queryWrapper = QueryWrapper.create();

        if (minAge != null) {
            queryWrapper.where(Account::getAge).ge(minAge);
        }
        if (status != null) {
            queryWrapper.and(Account::getStatus).eq(status);
        }
        
        queryWrapper.orderBy(column("id").asc());

        List<Account> accounts = accountMapper.selectListByQuery(queryWrapper);

        assertEquals(3, accounts.size());
        assertEquals("sarah", accounts.get(0).getUserName());
        assertEquals("emma", accounts.get(1).getUserName());
        assertEquals("tom", accounts.get(2).getUserName());
    }

    @Test
    public void whenPaginating_thenPageMetadataAndRecordsAreReturned() {
        QueryWrapper queryWrapper = QueryWrapper.create()
          .where(Account::getAge).ge(18)
          .orderBy(column("id").asc());

        Page<Account> page = accountMapper.paginate(1, 2, queryWrapper);

        assertEquals(2, page.getRecords().size());
        assertEquals(3L, page.getTotalRow());
        assertEquals(2L, page.getTotalPage());
    }

}
