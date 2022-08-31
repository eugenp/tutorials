package com.baeldung;

import com.baeldung.boot.Application;
import com.baeldung.boot.domain.GenericEntity;
import com.baeldung.boot.repository.GenericEntityRepository;
import com.baeldung.config.H2TestProfileJPAConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
public class SpringBootProfileIntegrationTest {
    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetrieveEntity_thenOK() {
        GenericEntity genericEntity = genericEntityRepository.save(new GenericEntity("test"));
        GenericEntity foundEntity = genericEntityRepository.findById(genericEntity.getId()).orElse(null);
        assertNotNull(foundEntity);
        assertEquals(genericEntity.getValue(), foundEntity.getValue());
    }
}
