package com.baeldung.arrayscollections;

import com.baeldung.arrayscollections.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.model.domain.internal.MappingMetamodelImpl;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("customdialect")
class HibernateCollectionsCustomDialectUnitTest {
    static int JSON_TYPE_CODE = 3001;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void givenCustomDialect_whenGetUserEntityFieldsTypes_thenExpectedTypeShouldBePresent() {
        MappingMetamodelImpl mapping = (MappingMetamodelImpl) entityManager.getMetamodel();

        EntityMappingType entityMappingType = mapping
          .getEntityDescriptor(User.class.getName())
          .getEntityMappingType();

        entityMappingType.getAttributeMappings()
          .forEach(attributeMapping -> {
              if (attributeMapping.getAttributeName().equals("tags")) {
                  JdbcType jdbcType = attributeMapping.getSingleJdbcMapping().getJdbcType();
                  assertEquals(JSON_TYPE_CODE, jdbcType.getJdbcTypeCode());
              }
          });
    }
}
