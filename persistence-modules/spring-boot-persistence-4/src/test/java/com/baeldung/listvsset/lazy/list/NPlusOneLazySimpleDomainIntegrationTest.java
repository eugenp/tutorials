package com.baeldung.listvsset.lazy.list;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

import com.baeldung.listvsset.BaseNPlusOneIntegrationTest;
import com.baeldung.listvsset.util.TestConfig;
import com.baeldung.nplusone.defaultfetch.list.Application;
import com.baeldung.nplusone.defaultfetch.list.Post;
import com.baeldung.nplusone.defaultfetch.list.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=false",
  "logging.level.org.hibernate.SQL=info",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneLazySimpleDomainIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    @Test
    void givenLazyListBasedUser_WhenFetchingAllUsers_ThenIssueOneRequests() {
        getUserService().findAll();
        assertSelectCount(1);
    }

    @Test
    void givenLazyListBasedUser_WhenFetchingAllUsersCheckingPosts_ThenIssueNPlusOneRequests() {
        int numberOfRequests = getUserService().countNumberOfRequestsWithFunction(users -> {
            List<List<Post>> usersWithPosts
              = users.stream()
              .map(User::getPosts)
              .filter(List::isEmpty)
              .toList();
            return users.size();
        });
        assertSelectCount(numberOfRequests + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenLazyListBasedUser_WhenFetchingOneUser_ThenIssueTwoRequest(Long id) {
        getUserService().getUserByIdWithPredicate(id, user -> !user.getPosts().isEmpty());
        assertSelectCount(2);
    }


    protected void addUsers() {
        List<User> users = jsonUtils.getUsers(User.class);
        databaseUtil.saveAll(users);
    }

}
