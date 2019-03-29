package com.baeldung.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = LoadResourceConfig.class)
public class LoadResourceAsStringIntegrationTest {

    private static final String ORIGINAL_DESCRIPTION = "This is a resource text file. This file will be loaded as a " +
            "resource and use its contents as a string.";

    @Autowired
    @Qualifier("resourceStringUsingSpel")
    private String resourceStringUsingSpel;

    @Autowired
    @Qualifier("resourceStringUsingFileCopyUtils")
    private String resourceStringUsingFileCopyUtils;

    @Autowired
    @Qualifier("resourceStringUsingStreamUtils")
    private String resourceStringUsingStreamUtils;

    @Autowired
    @Qualifier("resourceStringUsingCommonsIo")
    private String resourceStringUsingCommonsIo;

    @Autowired
    @Qualifier("resourceStringUsingGuava")
    private String resourceStringUsingGuava;

    @Test
    public void givenUsingSpel_whenConvertingAResourceToAString_thenCorrect() {
        assertEquals(ORIGINAL_DESCRIPTION, resourceStringUsingSpel);
    }

    @Test
    public void givenUsingFileCopyUtils_whenConvertingAResourceToAString_thenCorrect() {
        assertEquals(ORIGINAL_DESCRIPTION, resourceStringUsingFileCopyUtils);
    }

    @Test
    public void givenUsingStreamUtils_whenConvertingAResourceToAString_thenCorrect() {
        assertEquals(ORIGINAL_DESCRIPTION, resourceStringUsingStreamUtils);
    }

    @Test
    public void givenUsingCommonsIo_whenConvertingAResourceToAString_thenCorrect() {
        assertEquals(ORIGINAL_DESCRIPTION, resourceStringUsingCommonsIo);
    }

    @Test
    public void givenUsingGuava_whenConvertingAResourceToAString_thenCorrect() {
        assertEquals(ORIGINAL_DESCRIPTION, resourceStringUsingGuava);
    }
}
