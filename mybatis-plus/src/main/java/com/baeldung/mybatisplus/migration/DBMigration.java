package com.baeldung.mybatisplus.migration;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.ddl.SimpleDdl;

@Component
public class DBMigration extends SimpleDdl {

    @Override
    public List<String> getSqlFiles() {

        return Arrays.asList("db/db_v1.sql", "db/db_v2.sql");
    }

}