package com.baeldung.jpa.multiplebagfetchexception;

import org.hibernate.jpa.QueryHints;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultipleBagFetchExceptionIntegrationTest {

    private static EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-h2-multiple-bag-fetch-exception");
        entityManager = factory.createEntityManager();
        populateH2DB();
    }

    @Test
    public void whenFetchingMoreThanOneBag_thenThrowAnException() {
        IllegalArgumentException exception =
          assertThrows(IllegalArgumentException.class, () -> {
            String jpql = "SELECT dummy FROM DummyEntity dummy "
              + "JOIN FETCH dummy.collection1 "
              + "JOIN FETCH dummy.collection2 ";

            entityManager.createQuery(jpql);
        });

        final String expectedMessagePart = "MultipleBagFetchException";
        final String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessagePart));
    }

    @Test
    public void whenFetchingOneBagAndSet_thenRetrieveSuccess() {
        String jpql = "SELECT DISTINCT album FROM Album album "
          + "LEFT JOIN FETCH album.songs "
          + "LEFT JOIN FETCH album.followers "
          + "WHERE album.id = 1";

        Query query = entityManager.createQuery(jpql)
          .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false);

        assertEquals(1, query.getResultList().size());
    }

    @Test
    public void whenUsingMultipleQueries_thenRetrieveSuccess() {
        String jpql = "SELECT DISTINCT artist FROM Artist artist "
          + "LEFT JOIN FETCH artist.songs ";

        List<Artist> artists = entityManager.createQuery(jpql, Artist.class)
          .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
          .getResultList();

        jpql = "SELECT DISTINCT artist FROM Artist artist "
          + "LEFT JOIN FETCH artist.offers "
          + "WHERE artist IN :artists ";

        artists = entityManager.createQuery(jpql, Artist.class)
          .setParameter("artists", artists)
          .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
          .getResultList();

        assertEquals(2, artists.size());
    }

    @Test
    public void whenFetchingOneBagAndOneList_thenRetrieveSuccess() {
        String jpql = "SELECT DISTINCT user FROM User user "
          + "LEFT JOIN FETCH user.playlists "
          + "LEFT JOIN FETCH user.favoriteSongs ";

        List<User> users = entityManager.createQuery(jpql, User.class)
          .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
          .getResultList();

        assertEquals(3, users.size());
    }

    private static void populateH2DB() {
        entityManager.getTransaction().begin();

        Album album = new Album("album-name");
        Artist artist1 = new Artist("artist-name-1");
        Artist artist2 = new Artist("artist-name-2");
        artist2.createOffer("offer-name-1");
        artist2.createOffer("offer-name-2");
        entityManager.persist(album);
        entityManager.persist(artist1);
        entityManager.persist(artist2);

        Song song1 = new Song("song-name-1", artist2);
        song1.assignToAlbum(album);
        entityManager.persist(song1);

        User user1 = new User("user-name-1");
        user1.followAlbum(album);
        entityManager.persist(user1);

        User user2 = new User("user-name-2");
        user2.followAlbum(album);
        entityManager.persist(user2);

        User user3 = new User("user-name-3");
        user3.createPlaylist("playlist-name");
        user3.addSongToFavorites(song1);
        entityManager.persist(user3);

        entityManager.getTransaction().commit();
    }

}
