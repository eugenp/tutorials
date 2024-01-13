package com.baeldung.nplusone;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;

import com.baeldung.nplusone.eager.Application;
import com.baeldung.nplusone.eager.Comment;
import com.baeldung.nplusone.eager.Group;
import com.baeldung.nplusone.eager.Post;
import com.baeldung.nplusone.eager.User;
import com.baeldung.nplusone.util.TestConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class}, properties = {
  "hibernate.show_sql=true",
  "logging.level.org.hibernate.SQL=debug",
  "logging.level.org.hibernate.orm.jdbc.bind=trace"
})
class NPlusOneEagerIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    public static final String POSTS = "posts";
    public static final String COMMENTS = "comments";
    public static final String USERS = "users";

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenEagerUserWhenFetchingOneUserThenIssueNPlusOneRequests(Long id) {
        HashMap<String, Set<Long>> visitedMap = new HashMap<>();
        visitedMap.put(POSTS, new HashSet<>());
        visitedMap.put(COMMENTS, new HashSet<>());
        visitedMap.put(USERS, new HashSet<>());
        String indent = "";
        int numberOfRequests = getService()
          .getUserByIdWithFunction(id, s -> {
              int result = 1;
              if (s.isEmpty()) {
                  return result;
              } else {
                  User user = s.get();
                  System.out.println(indent + "Starting with the user #" + user.getId());
                  visitedMap.get(USERS).add(user.getId());
                  List<Post> posts = user.getPosts();
                  result += 1;
                  result += explorePosts(posts, visitedMap, indent + "\t");
              }
              return result;
          });
        assertSelectCount(numberOfRequests);
    }

    private int explorePosts(List<Post> posts, HashMap<String, Set<Long>> visitedMap, String indent) {
        int result = 0;
        if (posts == null || posts.isEmpty()) {
            return result;
        }
        for (Post post : posts) {
            if (!visitedMap.get(POSTS).contains(post.getId())) {
                System.out.println(indent + "Exploring post: #" + post.getId());
                result++;
                visitedMap.get(POSTS).add(post.getId());
                List<User> commenters = post.getComments().stream().map(Comment::getAuthor).collect(Collectors.toList());
                result += exploreUsers(commenters, visitedMap, indent + "\t");
            } else {
                System.out.println(indent + "Post #" + post.getId() + " already explored");
            }
        }
        return result;
    }

    private int exploreUsers(List<User> users, HashMap<String, Set<Long>> visitedMap, String indent) {
        int result = 0;
        if (users == null || users.isEmpty()) {
            return result;
        }
        for (User user : users) {
            if (!visitedMap.get(USERS).contains(user.getId())) {
                System.out.println(indent + "Exploring user: #" + user.getId());
                ++result;
                visitedMap.get(USERS).add(user.getId());
                result += explorePosts(user.getPosts(), visitedMap, indent + "\t");
                System.out.println(indent + "Adding user group: #" + user.getId());
                ++result;
            } else {
                System.out.println(indent + "User #" + user.getId() + " already explored");
            }
        }
        return result;
    }

    @ParameterizedTest
    @MethodSource
    void givenEagerUserWhenFetchingAllUsersThenIssueNPlusOneRequests(ToIntFunction<List<User>> function) {
        int numberOfRequests = getService().countNumberOfRequestsWithFunction(function);
        assertSelectCount(numberOfRequests);
    }

    static Stream<Arguments> givenEagerUserWhenFetchingAllUsersThenIssueNPlusOneRequests() {
        return Stream.of(
          Arguments.of((ToIntFunction<List<User>>) s -> {
              int result = 2 * s.size() + 1;
              List<Post> posts = s.stream().map(User::getPosts)
                .flatMap(List::stream)
                .collect(Collectors.toList());

              result += posts.size();
              return result;
          })
        );
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
