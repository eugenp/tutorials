package com.baeldung.listvsset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.listvsset.eager.list.fulldomain.Application;
import com.baeldung.listvsset.eager.list.fulldomain.Comment;
import com.baeldung.listvsset.eager.list.fulldomain.Group;
import com.baeldung.listvsset.eager.list.fulldomain.Post;
import com.baeldung.listvsset.eager.list.fulldomain.Profile;
import com.baeldung.listvsset.eager.list.fulldomain.User;
import com.baeldung.listvsset.util.JsonUtils;
import com.baeldung.listvsset.util.TestConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class, TestConfig.class})
class JsonUtilUnitTest {

    @Autowired
    private JsonUtils jsonUtils;

    @Test
    void givenFileWhenConvertingToUsersThenConversionIsCorrect() {
        List<User> users = jsonUtils.getUsers(User.class);
        assertThat(users).isNotEmpty();
        boolean wentThroughEverything = false;
        for (User user : users) {
            assertThat(user.getGroups()).isNotNull();
            Profile profile = user.getProfile();
            if (profile != null) {
                assertThat(user.getId()).isEqualTo(profile.getUser().getId());
            }
            for (Post post : user.getPosts()) {
                assertThat(user.getId()).isEqualTo(post.getAuthor().getId());
                if (post.getComments() != null) {
                    wentThroughEverything = true;
                    for (Comment comment : post.getComments()) {
                        assertThat(post.getId()).isEqualTo(comment.getPost().getId());
                    }
                }
            }
        }
        assertTrue(wentThroughEverything);
    }

    @Test
    void givenFileWhenConvertingToGroupsThenConversionIsCorrect() {
        List<Group> groups = jsonUtils.getGroups(Group.class);
        assertThat(groups).isNotEmpty();
    }


}
