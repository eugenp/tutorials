package com.baeldung.boot.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.Application;
import com.baeldung.boot.domain.MerchandiseEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class InventoryRepositoryIntegrationTest {

    private static final String ORIGINAL_TITLE = "Pair of Pants";
    private static final String UPDATED_TITLE = "Branded Luxury Pants";
    private static final String UPDATED_BRAND = "Armani";
    private static final String ORIGINAL_SHORTS_TITLE = "Pair of Shorts";

    @Autowired
    private InventoryRepository repository;

    @Test
    public void shouldCreateNewEntryInDB() {
        MerchandiseEntity pants = new MerchandiseEntity(ORIGINAL_TITLE, BigDecimal.ONE);
        pants = repository.save(pants);

        MerchandiseEntity shorts = new MerchandiseEntity(ORIGINAL_SHORTS_TITLE, new BigDecimal(3));
        shorts = repository.save(shorts);

        assertNotNull(pants.getId());
        assertNotNull(shorts.getId());
        assertNotEquals(pants.getId(), shorts.getId());
    }

    @Test
    public void shouldUpdateExistingEntryInDB() {
        MerchandiseEntity pants = new MerchandiseEntity(ORIGINAL_TITLE, BigDecimal.ONE);
        pants = repository.save(pants);

        Long originalId = pants.getId();

        pants.setTitle(UPDATED_TITLE);
        pants.setPrice(BigDecimal.TEN);
        pants.setBrand(UPDATED_BRAND);

        MerchandiseEntity result = repository.save(pants);

        assertEquals(originalId, result.getId());
        assertEquals(UPDATED_TITLE, result.getTitle());
        assertEquals(BigDecimal.TEN, result.getPrice());
        assertEquals(UPDATED_BRAND, result.getBrand());
    }
}
