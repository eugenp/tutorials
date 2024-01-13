package com.baeldung.listvsset;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.listvsset.lazy.list.Application;
import com.baeldung.listvsset.lazy.list.Comment;
import com.baeldung.listvsset.lazy.list.Group;
import com.baeldung.listvsset.lazy.list.Post;
import com.baeldung.listvsset.lazy.list.User;
import com.baeldung.listvsset.util.TestConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class})
class NPlusOneLazyListIntegrationTest extends BaseNPlusOneIntegrationTest<User> {

    private static final int TWO = 2;
    private static final int ONE = 1;

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenLazyListBasedUser_WhenTouchingOneToManyPostsOnOneUser_ThenIssueTwoRequest(Long id) {
        Optional<User> user = getService()
          .getUserByIdWithPredicate(id, s -> !s.getPosts().isEmpty());
        assertThat(user).isPresent();
        assertSelectCount(TWO);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void givenLazyListBasedUser_WhenTouchingOneToOneProfile_ThenIssueOneRequest(Long id) {
        Optional<User> user = getService()
          .getUserByIdWithPredicate(id, s -> !s.getProfile().getProfilePictureUrl().isEmpty());
        assertThat(user).isPresent();
        assertSelectCount(ONE);
    }

    @ParameterizedTest
    @MethodSource
    void givenLazyListBasedUser_WhenTouchingOneToManyPostsOnAllUsers_ThenIssueTwoNPlusOneRequests(ToIntFunction<List<User>> function) {
        int numberOfRequests = getService().countNumberOfRequestsWithFunction(function);
        assertSelectCount(numberOfRequests);
    }

    static Stream<Arguments> givenLazyListBasedUser_WhenTouchingOneToManyPostsOnAllUsers_ThenIssueTwoNPlusOneRequests() {
        return Stream.of(
          Arguments.of(
            (ToIntFunction<List<User>>) s -> {
                List<User> ignore = s.stream().filter(p -> !p.getPosts().isEmpty())
                  .toList();
                return s.size() * 2 + 1;
            }
          ),
          Arguments.of(
            (ToIntFunction<List<User>>) s -> {
                int result = s.size() * 2 + 1;
                List<Post> posts = s.stream().map(User::getPosts)
                  .flatMap(List::stream)
                  .toList();

                List<Comment> comments = posts.stream()
                  .map(Post::getComments)
                  .filter(c -> !c.isEmpty()).flatMap(List::stream)
                  .toList();

                result += posts.size();
                return result;
            }
          ),
          Arguments.of(
            (ToIntFunction<List<User>>) s -> {
                int result = s.size() * 2 + 1;
                List<Post> posts = s.stream().map(User::getPosts)
                  .flatMap(List::stream)
                  .toList();

                List<Comment> comments = posts.stream()
                  .map(Post::getComments)
                  .filter(c -> !c.isEmpty()).flatMap(List::stream)
                  .toList();

                List<String> emails = comments.stream().map(Comment::getAuthor).map(User::getEmail).toList();

                result += posts.size();
                return result;
            })
        );
    }

    protected void addUsers() {
        databaseUtil.saveAll(jsonUtils.getGroups(Group.class));
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
