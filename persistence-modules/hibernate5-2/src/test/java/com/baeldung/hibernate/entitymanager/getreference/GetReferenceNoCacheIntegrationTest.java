package com.baeldung.hibernate.entitymanager.getreference;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GetReferenceNoCacheIntegrationTest {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void setup(){
        entityManagerFactory = Persistence.createEntityManagerFactory("com.baeldung.hibernate.entitymanager.game_player");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new Game(1L, "Game 1"));
        entityManager.persist(new Game(2L, "Game 2"));
        entityManager.persist(new Player(1L,"Player 1"));
        entityManager.persist(new Player(2L, "Player 2"));
        entityManager.persist(new Player(3L, "Player 3"));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @BeforeEach
    public void beginTransaction(){
        // We create new persistence context for each test method to discard Hibernate first level cache.
        // So that we can see the behavior of getReference() method in a non-cached environment.
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @Test
    public void whenUsingFindMethodToUpdateGame_thenExecutesSelectForGame(){
        Game game1 = entityManager.find(Game.class, 1L);
        game1.setName("Game Updated 1");

        entityManager.persist(game1);
    }

    @Test
    public void whenUsingGetReferenceMethodToUpdateGame_thenExecutesSelectForGame(){
        Game game1 = entityManager.getReference(Game.class, 1L);
        game1.setName("Game Updated 2");

        entityManager.persist(game1);
    }

    @Test
    public void whenUsingFindMethodToDeletePlayer_thenExecutesSelectForPlayer(){
        Player player2 = entityManager.find(Player.class, 2L);
        entityManager.remove(player2);
    }

    @Test
    public void whenUsingGetReferenceMethodToDeletePlayer_thenExecutesSelectForPlayer(){
        Player player3 = entityManager.getReference(Player.class, 3L);
        entityManager.remove(player3);
    }

    @Test
    public void whenUsingFindMethodToUpdatePlayersGame_thenExecutesSelectForGame(){
        Game game1 = entityManager.find(Game.class, 1L);

        Player player1 = entityManager.find(Player.class, 1L);
        player1.setGame(game1);

        entityManager.persist(player1);
    }

    @Test
    public void whenUsingGetReferenceMethodToUpdatePlayersGame_thenDoesNotExecuteSelectForGame(){
        Game game2 = entityManager.getReference(Game.class, 2L);

        Player player1 = entityManager.find(Player.class, 1L);
        player1.setGame(game2);

        entityManager.persist(player1);
    }

    @AfterEach
    public void commitTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterAll
    public static void tearDown() {
        entityManagerFactory.close();
    }

}
