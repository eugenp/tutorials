package com.baeldung.boot.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.Application;
import com.baeldung.boot.domain.MerchandiseEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
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

    @Test
    @Transactional
    public void shouldUpdateExistingEntryInDBWithoutSave() {
        MerchandiseEntity pants = new MerchandiseEntity(ORIGINAL_TITLE, BigDecimal.ONE);
        pants = repository.save(pants);

        Long originalId = pants.getId();

        // Update using setters
        pants.setTitle(UPDATED_TITLE);
        pants.setPrice(BigDecimal.TEN);
        pants.setBrand(UPDATED_BRAND);

        Optional<MerchandiseEntity> resultOp = repository.findById(originalId);

        assertTrue(resultOp.isPresent());
        MerchandiseEntity result = resultOp.get();

        assertEquals(originalId, result.getId());
        assertEquals(UPDATED_TITLE, result.getTitle());
        assertEquals(BigDecimal.TEN, result.getPrice());
        assertEquals(UPDATED_BRAND, result.getBrand());
    }
}
