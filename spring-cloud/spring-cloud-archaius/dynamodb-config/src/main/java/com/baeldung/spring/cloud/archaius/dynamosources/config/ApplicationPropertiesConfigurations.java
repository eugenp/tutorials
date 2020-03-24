package com.baeldung.spring.cloud.archaius.dynamosources.config;

import java.util.Arrays;

import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.baeldung.spring.cloud.archaius.dynamosources.dynamodb.ArchaiusProperties;
import com.baeldung.spring.cloud.archaius.dynamosources.dynamodb.ArchaiusPropertiesRepository;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.DynamoDbConfigurationSource;

@Configuration
public class ApplicationPropertiesConfigurations {

    @Autowired
    AmazonDynamoDB amazonDynamoDb;

    @Autowired
    private ArchaiusPropertiesRepository repository;

    @Bean
    public AbstractConfiguration addApplicationPropertiesSource() {
        // Normally, the DB Table would be already created and populated.
        // In this case, we'll do it just before creating the archaius config source that uses it
        initDatabase();
        PolledConfigurationSource source = new DynamoDbConfigurationSource(amazonDynamoDb);
        return new DynamicConfiguration(source, new FixedDelayPollingScheduler());
    }

    private void initDatabase() {
        // Create the table
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDb);
        CreateTableRequest tableRequest = mapper.generateCreateTableRequest(ArchaiusProperties.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(amazonDynamoDb, tableRequest);

        // Populate the table
        ArchaiusProperties property = new ArchaiusProperties("baeldung.archaius.properties.one", "one FROM:dynamoDB");
        ArchaiusProperties property3 = new ArchaiusProperties("baeldung.archaius.properties.three", "three FROM:dynamoDB");
        repository.saveAll(Arrays.asList(property, property3));
    }
}
