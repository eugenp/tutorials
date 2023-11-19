package com.baeldung.spring.data.dynamodb.repository;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.baeldung.Application;
import com.baeldung.spring.data.dynamodb.model.ProductInfo;
import com.baeldung.spring.data.dynamodb.repositories.ProductInfoRepository;
import com.baeldung.spring.data.dynamodb.repository.rule.LocalDbCreationRule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class ProductInfoRepositoryIntegrationTest {

    @ClassRule
    public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();
    private static DynamoDBMapper dynamoDBMapper;
    private static AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private ProductInfoRepository repository;

    private static final String DYNAMODB_ENDPOINT = "amazon.dynamodb.endpoint";
    private static final String AWS_ACCESSKEY = "amazon.aws.accesskey";
    private static final String AWS_SECRETKEY = "amazon.aws.secretkey";

    private static final String EXPECTED_COST = "20";
    private static final String EXPECTED_PRICE = "50";

    @BeforeClass
    public static void setupClass() {
        Properties testProperties = loadFromFileInClasspath("application.properties")
            .filter(properties -> !isEmpty(properties.getProperty(AWS_ACCESSKEY)))
            .filter(properties -> !isEmpty(properties.getProperty(AWS_SECRETKEY)))
            .filter(properties -> !isEmpty(properties.getProperty(DYNAMODB_ENDPOINT)))
            .orElseThrow(() -> new RuntimeException("Unable to get all of the required test property values"));

        String amazonAWSAccessKey = testProperties.getProperty(AWS_ACCESSKEY);
        String amazonAWSSecretKey = testProperties.getProperty(AWS_SECRETKEY);
        String amazonDynamoDBEndpoint = testProperties.getProperty(DYNAMODB_ENDPOINT);

        amazonDynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
        amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    @Before
    public void setup() throws Exception {

        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ProductInfo.class);

            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

            amazonDynamoDB.createTable(tableRequest);
        } catch (ResourceInUseException e) {
            // Do nothing, table already created
        }

        // TODO How to handle different environments. i.e. AVOID deleting all entries in ProductInfo on table
        dynamoDBMapper.batchDelete((List<ProductInfo>) repository.findAll());
    }

    @Test
    public void givenItemWithExpectedCost_whenRunFindAll_thenItemIsFound() {

        ProductInfo productInfo = new ProductInfo(EXPECTED_COST, EXPECTED_PRICE);
        repository.save(productInfo);

        List<ProductInfo> result = (List<ProductInfo>) repository.findAll();
        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.get(0).getCost(), is(equalTo(EXPECTED_COST)));
    }

    private static boolean isEmpty(String inputString) {
        return inputString == null || "".equals(inputString);
    }

    private static Optional<Properties> loadFromFileInClasspath(String fileName) {
        InputStream stream = null;
        try {
            Properties config = new Properties();
            Path configLocation = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
            stream = Files.newInputStream(configLocation);
            config.load(stream);
            return Optional.of(config);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
