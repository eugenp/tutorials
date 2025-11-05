package com.baeldung.listvsset.list;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

import com.baeldung.listvsset.BaseNPlusOneIntegrationTest;
import com.baeldung.listvsset.eager.list.fulldomain.Application;
import com.baeldung.listvsset.eager.list.fulldomain.Comment;
import com.baeldung.listvsset.eager.list.fulldomain.Group;
import com.baeldung.listvsset.eager.list.fulldomain.Post;
import com.baeldung.listvsset.eager.list.fulldomain.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=false",
  "logging.level.org.hibernate.SQL=false",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneEagerFullDomainIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    public static final String POSTS = "posts";
    public static final String USERS = "users";

    @ParameterizedTest
    @MethodSource
    void givenEagerListBasedUser_WhenFetchingAllUsers_ThenIssueNPlusOneRequests(ToIntFunction<List<User>> function) {
        int numberOfRequests = getUserService().countNumberOfRequestsWithFunction(function);
        assertSelectCount(numberOfRequests);
    }

    static Stream<Arguments> givenEagerListBasedUser_WhenFetchingAllUsers_ThenIssueNPlusOneRequests() {
        return Stream.of(
          Arguments.of((ToIntFunction<List<User>>) s -> {
              int result = 2 * s.size() + 1;
              List<Post> posts = s.stream().map(User::getPosts)
                .flatMap(List::stream)
                .toList();

              result += posts.size();
              return result;
          })
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerListBasedUser_WhenFetchingOneUser_ThenUseDFS(Long id) {
        int numberOfRequests = getUserService().getUserByIdWithFunction(id, this::countNumberOfRequests);
        assertSelectCount(numberOfRequests);
    }

    private int countNumberOfRequests(User user) {
        HashMap<String, Set<Long>> visitedMap = new HashMap<>();
        visitedMap.put(POSTS, new HashSet<>());
        visitedMap.put(USERS, new HashSet<>());
        int result = 1;
        visitedMap.get(USERS).add(user.getId());
        List<Post> posts = user.getPosts();
        result += 1;
        result += explorePosts(posts, visitedMap);
        return result;

    }

    private int explorePosts(List<Post> posts, HashMap<String, Set<Long>> visitedMap) {
        int result = 0;
        if (posts == null || posts.isEmpty()) {
            return result;
        }
        for (Post post : posts) {
            if (!visitedMap.get(POSTS).contains(post.getId())) {
                result++;
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
                ++result;
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
                post.setComments(Collections.emptyList());
            }
        }
        databaseUtil.saveAll(users);
        // Handle non-existent users while adding comments
        databaseUtil.mergeAll(jsonUtils.getUsers(User.class));
    }

}
