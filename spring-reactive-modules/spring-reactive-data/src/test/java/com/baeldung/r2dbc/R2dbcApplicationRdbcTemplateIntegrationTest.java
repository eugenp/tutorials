package com.baeldung.r2dbc;

import com.baeldung.r2dbc.model.Player;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@RunWith(SpringRunner.class)
@SpringBootTest
public class R2dbcApplicationRdbcTemplateIntegrationTest {

    @Autowired
    DatabaseClient client;

    ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///testdb?options=DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=4;USER=sa;PASSWORD=");

    R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);

    @Before
    public void setup() {

        Hooks.onOperatorDebug();

        List<String> statements = Arrays.asList("DROP TABLE IF EXISTS player;", "CREATE table player (id INT AUTO_INCREMENT NOT NULL, name VARCHAR2, age INT NOT NULL);");

        statements.forEach(it -> client.sql(it)
            .fetch()
            .rowsUpdated()
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete());

    }

    @Test
    public void whenSearchForSaka_thenOneIsExpected() {

        insertPlayers();

        template.select(Player.class)
            .matching(query(where("name").is("Saka")))
            .one()
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();
    }

    @Test
    public void whenInsertThreePlayers_thenThreeAreExpected() {

        insertPlayers();

        template.select(Player.class)
            .all()
            .as(StepVerifier::create)
            .expectNextCount(3)
            .verifyComplete();
    }

    public void insertPlayers() {
        List<Player> players = Arrays.asList(new Player(null, "Saka", 22), new Player(null, "Pedro", 32), new Player(null, "Mbappé", 20));

        for (Player player : players) {
            template.insert(Player.class)
                .using(player)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        }

    }

}
