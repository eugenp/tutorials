package com.baeldung.mybatisvshibernate;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


class MybatisUnitTest {
    static SqlSessionFactory ssf;
    @BeforeAll
    public static void initialize() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        ssf = new SqlSessionFactoryBuilder().build(inputStream);
        myBatis3Setup();
    }
    private static void myBatis3Setup() throws IOException {
        try(SqlSession sf = ssf.openSession()) {
            ScriptRunner sr = new ScriptRunner(sf.getConnection());
            sr.runScript(new FileReader("src/test/resources/mybatis-create-db-and-table.sql"));
        }
    }

    @Test
    public void whenStudentsInsertedThenSearched() throws IOException {
        Student zeesh = new Student("Zeeshan Arif", 246, "zeesh.arif@gmail.com");
        Student bilal = new Student("Bilal Ahmed", 413, "xyz.123@gmail.com");
        Student zeeshFetched;
        Student bilalFetched;
        try (SqlSession ss = ssf.openSession()) {
            ss.insert("insertStudent", zeesh);
            ss.commit();
            ss.insert("insertStudent", bilal);
            ss.commit();
        }
        try (SqlSession ss = ssf.openSession()) {
            zeeshFetched = ss.selectOne("selectStudentById", zeesh.getId());
            bilalFetched = ss.selectOne("selectStudentById", bilal.getId());
        }
        Assertions.assertEquals(zeesh, zeeshFetched);
        Assertions.assertEquals(bilal, bilalFetched);
    }
}
