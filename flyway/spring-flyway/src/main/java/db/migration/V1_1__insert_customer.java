package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_1__insert_customer implements SpringJdbcMigration {

    final String INSERT_CUSTOMER = "insert into customer (first_name, last_name, email) values ('Florian', 'Eins','email@email.com');";

    @Override
    public void migrate(final JdbcTemplate jdbcTemplate) throws Exception {
        jdbcTemplate.execute(INSERT_CUSTOMER);
    }
}
