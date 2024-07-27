package com.baeldung.mybatisplus.migration;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.ddl.IDdl;

@Component
public class DBMigration implements IDdl {

    @Autowired
    private DataSource dataSource;

    @Override
    public void runScript(Consumer<DataSource> consumer) {
        consumer.accept(dataSource);
    }

    @Override
    public List<String> getSqlFiles() {
        
        // works for MySQL setup
        /*return Arrays.asList(
                "db/db_v1.sql",
                "db/db_v2.sql",
                "db/db_v3.sql"
        );*/

        return Collections.emptyList();
    }

}