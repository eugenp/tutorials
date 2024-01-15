package com.baeldung.listvsset;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

import com.baeldung.listvsset.eager.moderateset.Application;
import com.baeldung.listvsset.eager.moderateset.Group;
import com.baeldung.listvsset.eager.moderateset.GroupService;
import com.baeldung.listvsset.eager.moderateset.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=true",
  "logging.level.org.hibernate.SQL=debug",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneEagerModerateSetIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    private static final int ONE = 1;

    @Autowired
    private GroupService groupService;

    @Test
    void givenEagerSetBasedUser_whenFetchingAllUsers_thenIssueNPlusOneRequests() {
        List<User> users = getService().findAll();
        assertSelectCount(users.size() + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerSetBasedUser_whenFetchingOneUser_thenIssueNPlusOneRequests(Long id) {
        getService().getUserById(id);
        assertSelectCount(ONE);
    }

    @Test
    void givenEagerSetBasedGroup_whenFetchingAllGroups_thenIssueRequestsForEachGroup() {
        List<Group> groups = groupService.findAll();
        assertSelectCount(groups.size() + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerSetBasedGroup_whenFetchingAllGroups_thenCreateCartesianProductInOneQuery(Long groupId) {
        groupService.findById(groupId);
        assertSelectCount(1);
    }

    protected void addUsers() {
        List<User> users = jsonUtils.getUsers(User.class);
        databaseUtil.saveAll(users);
        List<Group> groups = jsonUtils.getGroupsWithMembers(Group.class);
        databaseUtil.saveAll(groups);
    }

}
