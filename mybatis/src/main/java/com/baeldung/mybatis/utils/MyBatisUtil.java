package com.baeldung.mybatis.utils;

import com.baeldung.mybatis.mapper.AddressMapper;
import com.baeldung.mybatis.mapper.PersonMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

public class MyBatisUtil {
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String URL = "jdbc:derby:testdb1;create=true";
	public static final String USERNAME = "sa";
	public static final String PASSWORD = "pass123";
	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory buildqlSessionFactory() {
		DataSource dataSource = new PooledDataSource(DRIVER, URL, USERNAME, PASSWORD);
		Environment environment = new Environment("Development", new JdbcTransactionFactory(), dataSource);
		Configuration configuration = new Configuration(environment);
		configuration.addMapper(PersonMapper.class);
		configuration.addMapper(AddressMapper.class);
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(configuration);
		return factory;

	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public String getPersonByName(String name) {
		return new SQL() {
			{
				SELECT("*");
				FROM("person");
				WHERE("name like #{name} || '%'");
			}
		}.toString();
	}
}
