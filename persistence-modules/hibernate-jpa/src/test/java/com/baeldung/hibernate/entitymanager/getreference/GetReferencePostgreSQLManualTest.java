package com.baeldung.hibernate.entitymanager.getreference;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We need to have a running PostgreSQL instance in order to execute this integration test
 */
public class GetReferencePostgreSQLManualTest {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static final PrintStream SystemOut = System.out;
    private static OutputStream output;

    @BeforeAll
    public static void setup() {
        // close some specific loggers so that we can clearly see Hibernate: SQL queries
        ((Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.OFF);
        ((Logger) LoggerFactory.getLogger("org.hibernate.type.descriptor.sql")).setLevel(Level.OFF);
        ((Logger) LoggerFactory.getLogger("org.hibernate.stat")).setLevel(Level.OFF);

        entityManagerFactory = Persistence.createEntityManagerFactory("com.baeldung.hibernate.entitymanager.game_player_postgresql");
    }

    private void runInTransaction(Runnable task) {
        // We create new persistence context for each test method to discard Hibernate first level cache.
        // So that we can see the behavior of getReference() method in a non-cached environment.
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        task.run();
        entityManager.getTransaction().commit();
        // In any case, we use clear() and close() to make all the managed entities detached and cleared.
        // So, we can be sure we test always on a clear persistence context.
        entityManager.clear();
        entityManager.close();
    }

    @BeforeEach
    public void beforeEach() {
        // stubbing System.out printStream
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void afterEach() {
        System.setOut(SystemOut);
        // we print to see original output after each test method
        System.out.print(output.toString());
    }

    @AfterAll
    public static void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    public void whenUsingFindMethodToUpdateGame_thenExecutesSelectForGame() {

        runInTransaction(() -> {
            Game game1 = entityManager.find(Game.class, 1L);
            game1.setName("Game Updated 1");

            entityManager.persist(game1);
        });

        StringBuilder expected = new StringBuilder();
        expected.append("Hibernate: select game0_.id as id1_0_0_, game0_.name as name2_0_0_ from Game game0_ where game0_.id=?\n");
        expected.append("Hibernate: update Game set name=? where id=?\n");

        assertEquals(expected.toString(), output.toString());
    }

    @Test
    public void whenUsingGetReferenceMethodToUpdateGame_thenExecutesSelectForGame() {

        runInTransaction(() -> {
            Game game1 = entityManager.getReference(Game.class, 1L);
            game1.setName("Game Updated 2");

            entityManager.persist(game1);
        });

        StringBuilder expected = new StringBuilder();
        expected.append("Hibernate: select game0_.id as id1_0_0_, game0_.name as name2_0_0_ from Game game0_ where game0_.id=?\n");
        expected.append("Hibernate: update Game set name=? where id=?\n");

        assertEquals(expected.toString(), output.toString());
    }

    @Test
    public void whenUsingFindMethodToDeletePlayer_thenExecutesSelectForPlayer() {

        runInTransaction(() -> {
            Player player2 = entityManager.find(Player.class, 2L);
            entityManager.remove(player2);
        });

        StringBuilder expected = new StringBuilder();
        expected.append("Hibernate: select ");
        expected.append("player0_.id as id1_1_0_, player0_.game_id as game_id3_1_0_, ");
        expected.append("player0_.name as name2_1_0_, game1_.id as id1_0_1_, game1_.name as name2_0_1_ ");
        expected.append("from Player player0_ left outer join Game game1_ on player0_.game_id=game1_.id where player0_.id=?\n");
        expected.append("Hibernate: delete from Player where id=?\n");

        assertEquals(expected.toString(), output.toString());
    }

    @Test
    public void whenUsingGetReferenceMethodToDeletePlayer_thenExecutesSelectForPlayer() {

        runInTransaction(() -> {
            Player player3 = entityManager.getReference(Player.class, 3L);
            entityManager.remove(player3);
        });

        StringBuilder expected = new StringBuilder();
        expected.append("Hibernate: select ");
        expected.append("player0_.id as id1_1_0_, player0_.game_id as game_id3_1_0_, ");
        expected.append("player0_.name as name2_1_0_, game1_.id as id1_0_1_, game1_.name as name2_0_1_ ");
        expected.append("from Player player0_ left outer join Game game1_ on player0_.game_id=game1_.id where player0_.id=?\n");
        expected.append("Hibernate: delete from Player where id=?\n");

        assertEquals(expected.toString(), output.toString());
    }

    @Test
    public void whenUsingFindMethodToUpdatePlayersGame_thenExecutesSelectForGame() {

        runInTransaction((() -> {
            Game game1 = entityManager.find(Game.class, 1L);

            Player player1 = entityManager.find(Player.class, 1L);
            player1.setGame(game1);

            entityManager.persist(player1);
        }));

        StringBuilder expected = new StringBuilder();
        expected.append("Hibernate: select game0_.id as id1_0_0_, game0_.name as name2_0_0_ from Game game0_ where game0_.id=?\n");
        expected.append("Hibernate: select ");
        expected.append("player0_.id as id1_1_0_, player0_.game_id as game_id3_1_0_, ");
        expected.append("player0_.name as name2_1_0_, game1_.id as id1_0_1_, game1_.name as name2_0_1_ ");
        expected.append("from Player player0_ left outer join Game game1_ on player0_.game_id=game1_.id where player0_.id=?\n");
        expected.append("Hibernate: update Player set game_id=?, name=? where id=?\n");

        assertEquals(expected.toString(), output.toString());
    }

    @Test
    public void whenUsingGetReferenceMethodToUpdatePlayersGame_thenDoesNotExecuteSelectForGame() {

        runInTransaction(() -> {
            Game game2 = entityManager.getReference(Game.class, 2L);

            Player player1 = entityManager.find(Player.class, 1L);
            player1.setGame(game2);

            entityManager.persist(player1);
        });

        StringBuilder expected = new StringBuilder();
        expected.append("Hibernate: select ");
        expected.append("player0_.id as id1_1_0_, player0_.game_id as game_id3_1_0_, ");
        expected.append("player0_.name as name2_1_0_, game1_.id as id1_0_1_, game1_.name as name2_0_1_ ");
        expected.append("from Player player0_ left outer join Game game1_ on player0_.game_id=game1_.id where player0_.id=?\n");
        expected.append("Hibernate: update Player set game_id=?, name=? where id=?\n");

        assertEquals(expected.toString(), output.toString());
    }

}
