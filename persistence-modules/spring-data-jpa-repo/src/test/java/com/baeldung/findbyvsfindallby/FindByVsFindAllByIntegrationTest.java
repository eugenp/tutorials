package com.baeldung.spring.data.persistence.findbyvsfindallby;

import com.baeldung.spring.data.persistence.findbyvsfindallby.repository.PlayerRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = FindByVsFindAllByApplication.class)
public class FindByVsFindAllByIntegrationTest {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void givenPlayer_whenFindBy_andFindAllBy_thenReturnSameQuery() {
        playerRepository.findByScoreGreaterThan(5);
    }
}
