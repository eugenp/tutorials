package com.baeldung.listvsset.set;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

import com.baeldung.listvsset.BaseNPlusOneIntegrationTest;
import com.baeldung.listvsset.eager.set.fulldomain.Application;
import com.baeldung.listvsset.eager.set.fulldomain.Comment;
import com.baeldung.listvsset.eager.set.fulldomain.Group;
import com.baeldung.listvsset.eager.set.fulldomain.Post;
import com.baeldung.listvsset.eager.set.fulldomain.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=false",
  "logging.level.org.hibernate.SQL=info",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneEagerFullDomainJoinIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    public static final String POSTS = "posts";
    public static final String USERS = "users";

    @Test
    void givenEagerSetBasedUser_WhenFetchingAllUsers_ThenIssueNPlusOneRequests() {
        List<User> users = getUserService().findAll();
        assertSelectCount(users.size() + 1);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerUserWhenFetchingOneUserThenIssueNPlusOneRequestsWithCartesianProduct(Long id) {
        HashMap<String, Set<Long>> visitedMap = new HashMap<>();
        visitedMap.put(POSTS, new HashSet<>());
        visitedMap.put(USERS, new HashSet<>());
        int numberOfRequests = getUserService()
          .getUserByIdWithFunction(id, user -> {
              int result = 1;
              visitedMap.get(USERS).add(user.getId());
              Set<Post> posts = user.getPosts();
              result += explorePosts(posts, visitedMap);
              return result;
          });
        assertSelectCount(numberOfRequests);
    }

    private int explorePosts(Set<Post> posts, HashMap<String, Set<Long>> visitedMap) {
        int result = 0;
        if (posts == null || posts.isEmpty()) {
            return result;
        }
        for (Post post : posts) {
            if (!visitedMap.get(POSTS).contains(post.getId())) {
                visitedMap.get(POSTS).add(post.getId());
                List<User> commenters = post.getComments().stream().map(Comment::getAuthor).toList();
                result += exploreUsers(commenters, visitedMap);
            }
        }
        return result;
    }

    private int exploreUsers(List<User> users, HashMap<String, Set<Long>> visitedMap) {
        int result = 0;
        if (users == null || users.isEmpty()) {
            return result;
        }
        for (User user : users) {
            if (!visitedMap.get(USERS).contains(user.getId())) {
                ++result;
                visitedMap.get(USERS).add(user.getId());
                result += explorePosts(user.getPosts(), visitedMap);
            }
        }
        return result;
    }

    protected void addUsers() {
        List<Group> groups = jsonUtils.getGroups(Group.class);
        databaseUtil.saveAll(groups);
        List<User> users = jsonUtils.getUsers(User.class);
        Map<Long, List<Comment>> comments = new HashMap<>();
        for (User user : users) {
            for (Post post : user.getPosts()) {
                if (!comments.containsKey(post.getId())) {
                    comments.put(post.getId(), new ArrayList<>());
                }
                comments.get(post.getId()).addAll(post.getComments());
                post.setComments(Collections.emptySet());
            }
        }
        databaseUtil.saveAll(users);
        // Handle non-existent users while adding comments
        databaseUtil.mergeAll(jsonUtils.getUsers(User.class));
    }

}
