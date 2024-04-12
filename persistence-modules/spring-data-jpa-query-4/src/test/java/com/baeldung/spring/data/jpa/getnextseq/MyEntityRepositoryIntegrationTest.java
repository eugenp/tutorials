package com.baeldung.spring.data.jpa.getnextseq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/testsequence.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MyEntityRepositoryIntegrationTest {
    @Autowired
    private MyEntityRepository myEntityRepository;

    @Autowired
    private MyEntityService myEntityService;

    @Test
    void whenUsingSequenceGenerator_thenNextValueReturned() {
        MyEntity entity = new MyEntity();
        myEntityRepository.save(entity);

        long generatedId = entity.getId();
        assertNotNull(generatedId);
        assertEquals(1L, generatedId);
    }


    @Test
    void whenUsingCustomQuery_thenNextValueReturned() {
        long generatedId = myEntityRepository.getNextSequenceValue();
        assertNotNull(generatedId);
        assertEquals(1L, generatedId);
    }

    @Test
    void whenUsingEntityManager_thenNextValueReturned() {
        long generatedId = myEntityService.getNextSequenceValue("my_sequence_name");
        assertNotNull(generatedId);
        assertEquals(1L, generatedId);
    }

}
