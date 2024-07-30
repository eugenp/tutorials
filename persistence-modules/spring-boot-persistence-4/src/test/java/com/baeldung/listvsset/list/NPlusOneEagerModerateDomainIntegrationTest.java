package com.baeldung.listvsset.list;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertDeleteCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertInsertCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.reset;
import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.listvsset.BaseNPlusOneIntegrationTest;
import com.baeldung.listvsset.eager.list.moderatedomain.Application;
import com.baeldung.listvsset.eager.list.moderatedomain.Group;
import com.baeldung.listvsset.eager.list.moderatedomain.GroupService;
import com.baeldung.listvsset.eager.list.moderatedomain.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
class NPlusOneEagerModerateDomainIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    @Autowired
    private GroupService groupService;

    @Test
    void givenEagerListBasedUser_whenFetchingAllUsers_thenIssueNPlusOneRequests() {
        List<User> users = getUserService().findAll();
        assertSelectCount(users.size() + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerListBasedUser_whenFetchingOneUser_thenIssueOneRequest(Long id) {
        getUserService().getUserById(id);
        assertSelectCount(1);
    }

    @Test
    void givenEagerListBasedGroup_whenFetchingAllGroups_thenIssueNPlusMPlusOneRequests() {
        List<Group> groups = groupService.findAll();
        Set<User> users = groups.stream().map(Group::getMembers).flatMap(List::stream).collect(Collectors.toSet());
        assertSelectCount(groups.size() + users.size() + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerListBasedGroup_whenFetchingAllGroups_thenIssueNPlusOneRequests(Long groupId) {
        Optional<Group> group = groupService.findById(groupId);
        assertThat(group).isPresent();
        assertSelectCount(1 + group.get().getMembers().size());
    }
    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerListBasedGroup_whenRemoveUser_thenRecreateGroup(Long groupId) {
        Optional<Group> optionalGroup = groupService.findById(groupId);
        assertThat(optionalGroup).isPresent();
        Group group = optionalGroup.get();
        List<User> members = group.getMembers();
        assertSelectCount(1 + members.size());
        if (!members.isEmpty()) {
            reset();
            members.remove(0);
            groupService.save(group);
            assertSelectCount(1 + members.size() + 1);
            assertDeleteCount(1);
            assertInsertCount(members.size());
        }
    }

    protected void addUsers() {
        List<User> users = jsonUtils.getUsers(User.class);
        databaseUtil.saveAll(users);
        List<Group> groups = jsonUtils.getGroupsWithMembers(Group.class);
        databaseUtil.saveAll(groups);
    }

}
