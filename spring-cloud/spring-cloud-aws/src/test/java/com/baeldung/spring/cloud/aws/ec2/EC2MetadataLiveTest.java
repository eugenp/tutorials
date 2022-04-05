package com.baeldung.spring.cloud.aws.ec2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;

/**
 * 
 * To run this Live Test, we need to have an AWS account and have API keys generated for programmatic access.
 * 
 * Check the README file in this module for more information.
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class EC2MetadataLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(EC2MetadataLiveTest.class);

    private boolean serverEc2;

    @Before
    public void setUp() {
        serverEc2 = Regions.getCurrentRegion() != null;
    }

    @Autowired
    private EC2Metadata eC2Metadata;

    @Autowired
    private AmazonEC2 amazonEC2;

    @Test
    public void whenEC2ClinentNotNull_thenSuccess() {
        assertThat(amazonEC2).isNotNull();
    }

    @Test
    public void whenEC2MetadataNotNull_thenSuccess() {
        assertThat(eC2Metadata).isNotNull();
    }

    @Test
    public void whenMetdataValuesNotNull_thenSuccess() {
        Assume.assumeTrue(serverEc2);
        assertThat(eC2Metadata.getAmiId()).isNotEqualTo("N/A");
        assertThat(eC2Metadata.getInstanceType()).isNotEqualTo("N/A");
    }

    @Test
    public void whenMetadataLogged_thenSuccess() {
        logger.info("Environment is EC2: {}", serverEc2);
        logger.info(eC2Metadata.toString());
    }
}
