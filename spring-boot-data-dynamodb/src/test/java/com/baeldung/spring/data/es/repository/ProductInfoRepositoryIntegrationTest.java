package com.baeldung.spring.data.es.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.baeldung.Application;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.baeldung.spring.data.es.model.ProductInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("local")
@TestPropertySource(properties = { "amazon.dynamodb.endpoint=http://localhost:8000/", "amazon.aws.accesskey=test1", "amazon.aws.secretkey=test231" })
public class ProductInfoRepositoryIntegrationTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private DynamoDB dynamoDB;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    ProductInfoRepository ProductInfoRepository;

    private static final String EXPECTED_COST = "20";
    private static final String EXPECTED_PRICE = "50";

    @Before
    @Ignore
    public void setUp() throws Exception {

        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ProductInfo.class); // 1

            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L)); // 2

            Table table = dynamoDB.createTable(tableRequest); // 3

            table.waitForActive(); // 4
        } catch (ResourceInUseException e) {
            // Do nothing, table already created
        }

        // TODO How to handle different environments. i.e. AVOID deleting all entries in ProductInfoion table
        dynamoDBMapper.batchDelete((List<ProductInfo>) ProductInfoRepository.findAll());
    }

    @Ignore
    @Test
    public void givenItemWithExpectedCost_whenRunFindAll_thenItemIsFound() {

        ProductInfo productInfo = new ProductInfo(EXPECTED_COST, EXPECTED_PRICE);
        ProductInfoRepository.save(productInfo);

        List<ProductInfo> result = (List<ProductInfo>) ProductInfoRepository.findAll();
        assertTrue("Not empty", result.size() > 0);
        assertTrue("Contains item with expected cost", result.get(0).getCost().equals(EXPECTED_COST));
    }
}
