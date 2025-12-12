package com.baeldung.mybatisdynamicsql;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class User extends SqlTable {
    public final SqlColumn<Integer> userId = column("user_id");
    public final SqlColumn<String> userName = column("username");
    public User() {
        super("users");
    }
}
