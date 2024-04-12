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

public class GetReferenceH2IntegrationTest {

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

        entityManagerFactory = Persistence.createEntityManagerFactory("com.baeldung.hibernate.entitymanager.game_player_h2");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new Game(1L, "Game 1"));
        entityManager.persist(new Game(2L, "Game 2"));
        entityManager.persist(new Player(1L, "Player 1"));
        entityManager.persist(new Player(2L, "Player 2"));
        entityManager.persist(new Player(3L, "Player 3"));

        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();
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
        expected.append("Hibernate: select g1_0.id,g1_0.name from Game g1_0 where g1_0.id=?" + System.lineSeparator());
        expected.append("Hibernate: update Game set name=? where id=?" + System.lineSeparator());

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
        expected.append("Hibernate: select g1_0.id,g1_0.name from Game g1_0 where g1_0.id=?" + System.lineSeparator());
        expected.append("Hibernate: update Game set name=? where id=?" + System.lineSeparator());

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
        expected.append("p1_0.id,g1_0.id,g1_0.name,p1_0.name ");
        expected.append("from Player p1_0 left join Game g1_0 on g1_0.id=p1_0.game_id where p1_0.id=?" + System.lineSeparator());
        expected.append("Hibernate: delete from Player where id=?" + System.lineSeparator());

        assertEquals(expected.toString(), output.toString());
    }

    @Test
    public void whenUsingGetReferenceMethodToDeletePlayer_thenExecutesSelectForPlayer() {

        runInTransaction(() -> {
            Player player3 = entityManager.getReference(Player.class, 3L);
            entityManager.remove(player3);
        });

        StringBuilder expected = new StringBuilder();
        expected.append("Hibernate: delete from Player where id=?" + System.lineSeparator());

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
        expected.append("Hibernate: select g1_0.id,g1_0.name from Game g1_0 where g1_0.id=?" + System.lineSeparator());
        expected.append("Hibernate: select ");
        expected.append("p1_0.id,g1_0.id,g1_0.name,p1_0.name ");
        expected.append("from Player p1_0 left join Game g1_0 on g1_0.id=p1_0.game_id where p1_0.id=?" + System.lineSeparator());
        expected.append("Hibernate: update Player set game_id=?,name=? where id=?" + System.lineSeparator());

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
        expected.append("p1_0.id,g1_0.id,g1_0.name,p1_0.name ");
        expected.append("from Player p1_0 left join Game g1_0 on g1_0.id=p1_0.game_id where p1_0.id=?" + System.lineSeparator());
        expected.append("Hibernate: update Player set game_id=?,name=? where id=?" + System.lineSeparator());

        assertEquals(expected.toString(), output.toString());
    }

}
