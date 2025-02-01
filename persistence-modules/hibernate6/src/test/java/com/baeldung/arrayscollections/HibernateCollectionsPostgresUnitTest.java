package com.baeldung.arrayscollections;

import com.baeldung.arrayscollections.entity.MigratingUser;
import com.baeldung.arrayscollections.entity.User;
import com.baeldung.arrayscollections.repository.MigratingUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.model.domain.internal.MappingMetamodelImpl;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("postgres")
class HibernateCollectionsPostgresUnitTest {

    static int ARRAY_TYPE_CODE = 2003;
    static int JSON_TYPE_CODE = 12;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MigratingUserRepository migratingUserRepository;

    @Test
    void givenMigratingUserRepository_whenMigrateTheUsers_thenAllTheUsersShouldBeSavedInDatabase() {
        prepareData();

        migratingUserRepository
          .findAll()
          .stream()
          .peek(u -> u.setNewTags(u.getTags()))
          .forEach(u -> migratingUserRepository.save(u));
    }

    private void prepareData() {
        List<MigratingUser> data = Stream.of("tag1", "tag2", "tag3")
        .map(
            t -> {
                MigratingUser migratingUser = new MigratingUser();
                migratingUser.setTags(List.of(t));

                return migratingUser;
            }
        )
        .toList();

        migratingUserRepository.saveAll(data);
    }

    @Test
    void givenPostgresDialect_whenGetUserEntityFieldsTypes_thenExpectedTypeShouldBePresent() {
        MappingMetamodelImpl mapping = (MappingMetamodelImpl) entityManager.getMetamodel();

        EntityMappingType entityMappingType = mapping
          .getEntityDescriptor(User.class.getName())
          .getEntityMappingType();

        entityMappingType.getAttributeMappings()
         .forEach(attributeMapping -> {
             if (attributeMapping.getAttributeName().equals("tags")) {
                 JdbcType jdbcType = attributeMapping.getSingleJdbcMapping().getJdbcType();
                 assertEquals(ARRAY_TYPE_CODE, jdbcType.getJdbcTypeCode());
             }
         });
    }
}
