package com.baeldung.hibernate.entitymanager.getreference;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GetReferenceWithCacheIntegrationTest {

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
    }

    @BeforeEach
    public void beginTransaction(){
        // We reuse the same persistence context so that we benefit Hibernate's first level cache
        entityManager.getTransaction().begin();
    }

    @Test
    public void whenUsingFindMethodToUpdateGame_thenDoesNotExecuteSelectForGame(){
        Game game1 = entityManager.find(Game.class, 1L);
        game1.setName("Game Updated 1");

        entityManager.persist(game1);
    }

    @Test
    public void whenUsingGetReferenceMethodToUpdateGame_thenDoesNotExecuteSelectForGame(){
        Game game1 = entityManager.getReference(Game.class, 1L);
        game1.setName("Game Updated 2");

        entityManager.persist(game1);
    }

    @Test
    public void whenUsingFindMethodToDeletePlayer_thenDoesNotExecuteSelectForPlayer(){
        Player player2 = entityManager.find(Player.class, 2L);
        entityManager.remove(player2);
    }

    @Test
    public void whenUsingGetReferenceMethodToDeletePlayer_thenDoesNotExecuteSelectForPlayer(){
        Player player3 = entityManager.getReference(Player.class, 3L);
        entityManager.remove(player3);
    }

    @Test
    public void whenUsingFindMethodToUpdatePlayersGame_thenDoesNotExecuteAnySelectQuery(){
        Game game2 = entityManager.find(Game.class, 2L);

        Player player1 = entityManager.find(Player.class, 1L);
        player1.setGame(game2);

        entityManager.persist(player1);
    }

    @Test
    public void whenUsingGetReferenceMethodToUpdatePlayersGame_thenDoesNotExecuteAnySelectQuery(){
        Game game1 = entityManager.getReference(Game.class, 1L);

        Player player1 = entityManager.find(Player.class, 1L);
        player1.setGame(game1);

        entityManager.persist(player1);
    }

    @AfterEach
    public void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    @AfterAll
    public static void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

}
