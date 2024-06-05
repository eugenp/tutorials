package com.baeldung.embeddedpostgresql;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.embedded.PreparedDbProvider;

@Configuration
@EnableJpaRepositories(basePackageClasses = PersonRepository.class)
@EntityScan(basePackageClasses = Person.class)
public class EmbeddedPostgresWithFlywayConfiguration {

    @Bean
    public DataSource dataSource() throws SQLException {
        return PreparedDbProvider
          .forPreparer(FlywayPreparer.forClasspathLocation("db/migrations"))
          .createDataSource();
    }
}
