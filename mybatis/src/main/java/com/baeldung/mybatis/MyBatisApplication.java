package com.baeldung.mybatis;

import com.baeldung.mybatis.model.Address;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisApplication {
    public static void main(String[] args) throws IOException {
        // Create a SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // Open a SqlSession
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Execute a query
            List<Address> addressList = session.selectList("com.baeldung.mybatis.mapper.AddressMapper.getAddresses", 1);

            for (Address address : addressList) {
                System.out.println(address);
            }
        }
    }

}

