package org.baeldung;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.baeldung.config.H2TestProfileJPAConfig;
import org.baeldung.domain.GenericEntity;
import org.baeldung.repository.GenericEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
public class SpringBootProfileIntegrationTest {
    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        GenericEntity genericEntity = genericEntityRepository.save(new GenericEntity("test"));
        GenericEntity foundEntity = genericEntityRepository.findOne(genericEntity.getId());
        assertNotNull(foundEntity);
        assertEquals(genericEntity.getValue(), foundEntity.getValue());
    }
}
