package com.baeldung.junit5.nested;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("given a article publication with three articles")
class OnlinePublicationUnitTest {
    private Publication publication;

    @BeforeEach
    void setupArticlesAndPublication() {
        Article freeArticle = new Article("free article", Membership.FREE);
        Article silverArticle = new Article("silver level article", Membership.SILVER);
        Article goldArticle = new Article("gold level article", Membership.GOLD);
        publication = new Publication(Arrays.asList(freeArticle, silverArticle, goldArticle));
    }

    @Test
    @DisplayName("then 3 articles are available")
    void shouldHaveThreeArticlesInTotal() {
        List<Article> allArticles = publication.getArticles();
        assertThat(allArticles).hasSize(3);
    }

    @Nested
    @DisplayName("when a user with a 'free' membership logs in")
    class UserWithAFreeMembership {
        User freeFreya = new User("Freya", Membership.FREE);

        @Test
        @DisplayName("then he should be able to read the 'free' articles")
        void shouldOnlyReadFreeArticles() {
            List<String> articles = publication.getReadableArticles(freeFreya);
            assertThat(articles).containsExactly("free article");
        }

        @Test
        @DisplayName("then he shouldn't be able to read the 'silver' and 'gold' articles")
        void shouldSeeSilverAndGoldLevelArticlesAsLocked() {
            List<String> articles = publication.getLockedArticles(freeFreya);
            assertThat(articles).containsExactlyInAnyOrder("silver level article", "gold level article");
        }
    }

    @Nested
    @DisplayName("when a user with a 'silver' membership logs in")
    class UserWithSilverMembership {
        User silverSilvester = new User("Silvester", Membership.SILVER);

        @Test
        @DisplayName("then he should be able to read the 'free' and 'silver' level articles")
        void shouldOnlyReadFreeAndSilverLevelArticles() {
            List<String> articles = publication.getReadableArticles(silverSilvester);
            assertThat(articles).containsExactlyInAnyOrder("free article", "silver level article");
        }

        @Test
        @DisplayName("then he should see the 'gold' level articles as locked")
        void shouldSeeGoldLevelArticlesAsLocked() {
            List<String> articles = publication.getLockedArticles(silverSilvester);
            assertThat(articles).containsExactlyInAnyOrder("gold level article");
        }
    }

    @Nested
    @DisplayName("when a user with a 'gold' membership logs in")
    class UserWithGoldMembership {
        User goldenGeorge = new User("George", Membership.GOLD);

        @Test
        @DisplayName("then he should be able to read all the articles")
        void shouldSeeAllArticles() {
            List<String> articles = publication.getReadableArticles(goldenGeorge);
            assertThat(articles).containsExactlyInAnyOrder("free article", "silver level article", "gold level article");
        }

        @Test
        @DisplayName("then he should not see any article as locked")
        void shouldNotHaveHiddenArticles() {
            List<String> articles = publication.getLockedArticles(goldenGeorge);
            assertThat(articles).isEmpty();
        }

    }

}