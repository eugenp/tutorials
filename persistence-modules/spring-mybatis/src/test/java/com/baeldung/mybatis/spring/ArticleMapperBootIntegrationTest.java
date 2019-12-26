package com.baeldung.mybatis.spring;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = PersistenceAutoConfig.class)
public class ArticleMapperBootIntegrationTest extends ArticleMapperCommonTest {

}
