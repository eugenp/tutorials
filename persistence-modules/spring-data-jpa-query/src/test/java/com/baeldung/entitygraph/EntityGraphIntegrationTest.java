package com.baeldung.entitygraph;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.entitygraph.model.Characteristic;
import com.baeldung.entitygraph.model.Item;
import com.baeldung.entitygraph.repository.CharacteristicsRepository;
import com.baeldung.entitygraph.repository.ItemRepository;

@DataJpaTest(showSql = false)
@RunWith(SpringRunner.class)
@Sql(scripts = "/entitygraph-data.sql")
public class EntityGraphIntegrationTest {
   
    @Autowired
    private ItemRepository itemRepo;
    
    @Autowired
    private CharacteristicsRepository characteristicsRepo;
    
    @Test
    public void givenEntityGraph_whenCalled_shouldRetrunDefinedFields() {
        Item item = itemRepo.findByName("Table");
        assertThat(item.getId()).isEqualTo(1L);
    }
    
    @Test
    public void givenAdhocEntityGraph_whenCalled_shouldRetrunDefinedFields() {
        Characteristic characteristic = characteristicsRepo.findByType("Rigid");
        assertThat(characteristic.getId()).isEqualTo(1L);
    }
}
