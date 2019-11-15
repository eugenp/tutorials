package com.baeldung;

import com.baeldung.h2db.springboot.SpringBootH2Application;
import com.baeldung.h2db.springboot.daos.ItemRepository;
import com.baeldung.h2db.springboot.models.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootH2Application.class)
public class ItemIntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void shouldNotAllowToPersistNullItemsPrice() {
        Item saved = null;
        try {
            saved = itemRepository.save(new Item());
        } catch (Exception e) {
            assertThat(saved).isNull();
            assertThat(e).hasStackTraceContaining("must not be null");
        }
    }
}
