package com.baeldung.spring.pkl;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringPklUnitTest {
    Logger logger = LoggerFactory.getLogger(SpringPklUnitTest.class);

    @Autowired
    private GitHubService gitHubService;
    @Test
    public void test() {
        String url = gitHubService.getToolIntegration().url;
        logger.info("URL: " + url);
    }

}
