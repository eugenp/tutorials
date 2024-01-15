package com.baeldung.listvsset;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

import com.baeldung.listvsset.eager.simplelist.Application;
import com.baeldung.listvsset.eager.simplelist.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=true",
  "logging.level.org.hibernate.SQL=debug",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneEagerSimpleListIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    private static final int ONE = 1;

    @Test
    void givenEagerListBasedUser_WhenFetchingAllUsers_ThenIssueNPlusOneRequests() {
        List<User> users = getService().findAll();
        assertSelectCount(users.size() + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerListBasedUser_WhenFetchingOneUser_ThenIssueNPlusOneRequests(Long id) {
        getService().getUserById(id);
        assertSelectCount(ONE);
    }

    protected void addUsers() {
        List<User> users = jsonUtils.getUsers(User.class);
        databaseUtil.saveAll(users);
    }

}
