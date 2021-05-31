package org.hexagonal.dd.utils;

public interface Constants {
    String INSERT_ARTICLE = "insert into article (id, title, author) " +
            "values (:id, :title, :author)";
    String DELETE_ARTICLE = "delete from article where id = :id";

    String UPDATE_ARTICLE = "update article " +
            "set title = :title, author = :author " +
            "where id = :id";

    String GET_ARTICLE_BY_ID = "select id, title, author from article where id = :id";

    String GET_TOP_10_ARTICLES = "select id, title, author from article limit 10";

    String CREATE_TABLE = "DROP TABLE IF EXISTS article; " +
            "CREATE TABLE article (\n" +
            "            id VARCHAR(250),\n" +
            "    title VARCHAR(250),\n" +
            "    author VARCHAR(250)\n" +
            ")";



}
