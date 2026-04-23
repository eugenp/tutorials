package com.baeldung.mybatisdynamicsql;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public class Post extends SqlTable {
    public final SqlColumn<Integer> postId = column("post_id");
    public final SqlColumn<Integer> posterId = column("poster_id");
    public Post() {
        super("posts");
    }
}
