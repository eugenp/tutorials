package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V1_0__create_table_customer implements SpringJdbcMigration {

    final String CREATE_TABLE_CUSTOMER = "create table if not exists customer ( id bigint AUTO_INCREMENT not null primary key, first_name varchar(255) , last_name  varchar(255) , email varchar(255) );";

    @Override
    public void migrate(final JdbcTemplate jdbcTemplate) throws Exception {
        jdbcTemplate.execute(CREATE_TABLE_CUSTOMER);
    }
}
