package com.baeldung.spring.data.persistence.findbyvsfindallby;

import com.baeldung.spring.data.persistence.findbyvsfindallby.model.Player;
import com.baeldung.spring.data.persistence.findbyvsfindallby.repository.PlayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FindByVsFindAllByApplication.class, properties = "spring.jpa.show-sql=true")
public class FindByVsFindAllByIntegrationTest {
    @Autowired
    private PlayerRepository playerRepository;

    @Before
    public void setup() {
        Player player1 = new Player(600);
        Player player2 = new Player(500);
        Player player3 = new Player(300);
        playerRepository.saveAll(Arrays.asList(player1, player2, player3));
    }

    @Test
    public void givenSavedPlayer_whenUseFindByOrFindAllBy_thenReturnSameResult() {
        List<Player> findByPlayers = playerRepository.findByScoreGreaterThan(400);
        List<Player> findAllByPlayers = playerRepository.findAllByScoreGreaterThan(400);
        assertEquals(findByPlayers, findAllByPlayers);
    }

    @Test
    public void givenSavedPlayer_whenUseFindFirst_thenReturnSingleResult() {
        Optional<Player> player = playerRepository.findFirstByScoreGreaterThan(400);
        assertTrue(player.isPresent());
        assertEquals(600, player.get().getScore());
    }
}
