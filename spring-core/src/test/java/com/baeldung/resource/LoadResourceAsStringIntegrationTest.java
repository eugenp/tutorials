package com.baeldung.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = LoadResourceConfig.class)
public class LoadResourceAsStringIntegrationTest {

    private static final String EXPECTED_RESOURCE_VALUE = "This is a resource text file. This file will be loaded as a " + "resource and use its contents as a string.";

    @Value("#{T(com.baeldung.resource.ResourceUtils).readFileToString('classpath:resource.txt')}")
    private String resourceStringUsingSpel;

    @Autowired
    @Qualifier("resourceStringUsingFileCopyUtils")
    private String resourceStringUsingFileCopyUtils;

    @Autowired
    @Qualifier("resourceStringUsingStreamUtils")
    private String resourceStringUsingStreamUtils;

    @Test
    public void givenUsingSpel_whenConvertingAResourceToAString_thenCorrect() {
        assertEquals(EXPECTED_RESOURCE_VALUE, resourceStringUsingSpel);
    }

    @Test
    public void givenUsingFileCopyUtils_whenConvertingAResourceToAString_thenCorrect() {
        assertEquals(EXPECTED_RESOURCE_VALUE, resourceStringUsingFileCopyUtils);
    }

    @Test
    public void givenUsingStreamUtils_whenConvertingAResourceToAString_thenCorrect() {
        assertEquals(EXPECTED_RESOURCE_VALUE, resourceStringUsingStreamUtils);
    }
}
