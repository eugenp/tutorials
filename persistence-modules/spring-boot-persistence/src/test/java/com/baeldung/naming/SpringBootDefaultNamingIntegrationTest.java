package com.baeldung.naming;

import com.baeldung.naming.entity.Account;
import org.assertj.core.api.SoftAssertions;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
  "spring.jpa.hibernate.naming.physical-strategy=org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
  "spring.jpa.hibernate.naming.implicit-strategy=org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"
})
public class SpringBootDefaultNamingIntegrationTest extends NamingConfig {

    @Test
    public void givenDefaultBootNamingStrategy_whenCreateDatabase_thenGetStrategyNames() {
        Metadata metadata = MetadataExtractorIntegrator.INSTANCE.getMetadata();
        String entity = Account.class.getCanonicalName();
        PersistentClass persistentClass = metadata.getEntityBinding(entity);
        Table table = persistentClass.getTable();
        String physicalNameExpected = "secondary_email";
        String implicitNameExpected = "default_email";
        String tableNameExpected = "account";

        String tableNameCreated = table.getName();
        String physicalNameCreated = table
          .getColumn(3)
          .getName();
        String implicitNameCreated = table
          .getColumn(2)
          .getName();

        SoftAssertions softly = new SoftAssertions();
        softly
          .assertThat(tableNameCreated)
          .isEqualTo(tableNameExpected);
        softly
          .assertThat(physicalNameCreated)
          .isEqualTo(physicalNameExpected);
        softly
          .assertThat(implicitNameCreated)
          .isEqualTo(implicitNameExpected);
        softly.assertAll();
    }
}
