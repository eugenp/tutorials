package com.baeldung.seda.springintegration;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.demo.config.ChannelConfiguration;
import com.example.demo.config.IntegrationConfiguration;
import com.example.demo.config.TaskExecutorConfiguration;
import com.example.demo.config.TestGateway;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { TaskExecutorConfiguration.class, ChannelConfiguration.class, IntegrationConfiguration.class })
@EnableIntegration
@IntegrationComponentScan(basePackages = { "com.example.demo.config" })
public class SpringIntegrationSedaUnitTest {

    @Autowired
    TestGateway testGateway;

    @Test
    void givenTextWithCapitalAndSmallCaseAndWithoutDuplicateWords_whenCallingCountWordOnGateway_thenWordCountReturnedAsMap() {
        Map<String, Long> actual = testGateway.countWords("My name is Hesam");
        Map<String, Long> expected = new HashMap<>();
        expected.put("my", 1L);
        expected.put("name", 1L);
        expected.put("is", 1L);
        expected.put("hesam", 1L);
        org.junit.Assert.assertEquals(expected, actual);
    }

    @Test
    void givenTextWithDuplicateWords_whenCallingCountWordOnGateway_thenWordCountReturnedAsMap() {
        Map<String, Long> actual = testGateway.countWords("the dog chased the rabbit into the jungle");
        Map<String, Long> expected = new HashMap<>();
        expected.put("the", 3L);
        expected.put("dog", 1L);
        expected.put("chased", 1L);
        expected.put("rabbit", 1L);
        expected.put("into", 1L);
        expected.put("jungle", 1L);

        org.junit.Assert.assertEquals(expected, actual);
    }

}
