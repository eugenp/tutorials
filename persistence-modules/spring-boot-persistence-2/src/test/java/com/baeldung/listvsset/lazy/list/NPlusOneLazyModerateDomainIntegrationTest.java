package com.baeldung.listvsset.lazy.list;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.listvsset.BaseNPlusOneIntegrationTest;
import com.baeldung.listvsset.lazy.list.moderatedomain.Application;
import com.baeldung.listvsset.lazy.list.moderatedomain.Group;
import com.baeldung.listvsset.lazy.list.moderatedomain.GroupService;
import com.baeldung.listvsset.lazy.list.moderatedomain.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=false",
  "logging.level.org.hibernate.SQL=info",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneLazyModerateDomainIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    @Autowired
    private GroupService groupService;

    @Test
    void givenLazyListBasedUser_whenFetchingAllUsers_thenIssueOneRequest() {
        getUserService().findAll();
        assertSelectCount(1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenLazyListBasedUser_whenFetchingOneUser_thenIssueOneRequest(Long id) {
        getUserService().getUserById(id);
        assertSelectCount(1);
    }

    @Test
    void givenLazyListBasedGroup_whenFetchingAllGroups_thenIssueOneRequest() {
        groupService.findAll();
        assertSelectCount(1);
    }
    @Test
    void givenLazyListBasedGroup_whenFilteringGroups_thenIssueNPlusOneRequests() {
        int numberOfRequests = groupService.countNumberOfRequestsWithFunction(groups -> {
            groups.stream()
              .map(Group::getMembers)
              .flatMap(Collection::stream)
              .collect(Collectors.toSet());
            return groups.size();
        });
        assertSelectCount(numberOfRequests + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenLazyListBasedGroup_whenFetchingAllGroups_thenIssueOneRequest(Long groupId) {
        Optional<Group> group = groupService.findById(groupId);
        assertThat(group).isPresent();
        assertSelectCount(1);
    }

    protected void addUsers() {
        List<User> users = jsonUtils.getUsers(User.class);
        databaseUtil.saveAll(users);
        List<Group> groups = jsonUtils.getGroupsWithMembers(Group.class);
        databaseUtil.saveAll(groups);
    }

}
