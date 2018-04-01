package com.mybatis3.util;

import java.io.IOException;
import java.io.InputStream;

import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.mybatis3.domain.Student;
import com.mybatis3.mappers.StudentMapper;
import com.mybatis3.typehandlers.PhoneTypeHandler;


/**
 * @author Siva
 *
 */
public class MyBatisUtil
{
	private static SqlSessionFactory xmlSqlSessionFactory;
	private static SqlSessionFactory javaSqlSessionFactory;
	
	public static SqlSessionFactory getSqlSessionFactoryUsingXML()
	{
		if(xmlSqlSessionFactory==null) 
		{
			InputStream inputStream;
			try
			{
				inputStream = Resources.getResourceAsStream("mybatis-config.xml");
				xmlSqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			}catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return xmlSqlSessionFactory;
	}
	
	public static SqlSessionFactory getSqlSessionFactoryUsingJavaAPI()
	{
		if(javaSqlSessionFactory==null) 
		{
			try
			{
				DataSource dataSource = DataSourceFactory.getDataSource();
				TransactionFactory transactionFactory = new JdbcTransactionFactory();
				Environment environment = new Environment("development", transactionFactory, dataSource);
				Configuration configuration = new Configuration(environment);
				configuration.getTypeAliasRegistry().registerAlias("student", Student.class);
				configuration.getTypeHandlerRegistry().register(PhoneTypeHandler.class);
				configuration.addMapper(StudentMapper.class);
				javaSqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
				
			}catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		return javaSqlSessionFactory;
	}
	
}
