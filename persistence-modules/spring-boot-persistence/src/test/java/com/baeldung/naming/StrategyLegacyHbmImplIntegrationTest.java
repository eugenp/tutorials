package com.baeldung.naming;

import com.baeldung.naming.entity.Preference;
import org.assertj.core.api.SoftAssertions;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
  "spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl",
  "spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyHbmImpl",
})
public class StrategyLegacyHbmImplIntegrationTest extends NamingConfig {

    @Test
    public void givenLegacyHbmImplNamingNamingStrategy_whenCreateDatabase_thenGetStrategyNames() {
        Metadata metadata = MetadataExtractorIntegrator.INSTANCE.getMetadata();
        String entity = Preference.class.getCanonicalName();
        PersistentClass persistentClass = metadata.getEntityBinding(entity);
        Collection<Table> tables = metadata
          .getDatabase()
          .getDefaultNamespace()
          .getTables();
        Table preferenceTable = persistentClass.getTable();
        String tableNameExpected = "Account_preferences";
        Table accountPreferencesTable = tables
          .stream()
          .filter(table -> table
            .getName()
            .equals(tableNameExpected))
          .findFirst()
          .get();
        String implicitNameExpected = "account";

        String implicitNameCreated = preferenceTable
          .getColumn(3)
          .getName();
        String tableNameCreated = accountPreferencesTable.getName();

        SoftAssertions.assertSoftly(softly -> {
            softly
              .assertThat(implicitNameCreated)
              .isEqualTo(implicitNameExpected);
            softly
              .assertThat(tableNameCreated)
              .isEqualTo(tableNameExpected);
        });
    }
}
