package com.baeldung.dynamodb.query;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserOrdersRepositoryLiveTest {

    private static final String TABLE_NAME = "UserOrders";
    private DynamoDbClient dynamoDb;
    private UserOrdersRepository repository;

    private final LocalStackContainer localstack = new LocalStackContainer(
      DockerImageName.parse("localstack/localstack:latest"))
      .withServices(LocalStackContainer.Service.DYNAMODB);

    @BeforeAll
    void setUp() {
        localstack.start();

        dynamoDb = DynamoDbClient.builder()
          .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.DYNAMODB))
          .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("test", "test")))
          .region(Region.of(localstack.getRegion()))
          .build();

        repository = new UserOrdersRepository(dynamoDb);

        createTable();
        seedData();
    }

    void createTable() {
        dynamoDb.createTable(CreateTableRequest.builder()
          .tableName(TABLE_NAME)
          .keySchema(
            KeySchemaElement.builder().attributeName("userId").keyType(KeyType.HASH).build(),
            KeySchemaElement.builder().attributeName("orderDate").keyType(KeyType.RANGE).build()
          )
          .attributeDefinitions(
            AttributeDefinition.builder().attributeName("userId").attributeType(ScalarAttributeType.S).build(),
            AttributeDefinition.builder().attributeName("orderDate").attributeType(ScalarAttributeType.S).build()
          )
          .provisionedThroughput(
            ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build()
          )
          .build());

        dynamoDb.waiter().waitUntilTableExists(r -> r.tableName(TABLE_NAME));
    }

    void seedData() {
        putOrder("2024-12-01", "Laptop");
        putOrder("2024-12-15", "Monitor");
        putOrder("2025-01-05", "Mouse");
        putOrder("2025-01-20", "Keyboard");
    }

    void putOrder(String orderDate, String itemName) {
        dynamoDb.putItem(PutItemRequest.builder()
          .tableName(TABLE_NAME)
          .item(Map.of(
            "userId", AttributeValue.fromS("user1"),
            "orderDate", AttributeValue.fromS(orderDate),
            "item", AttributeValue.fromS(itemName)
          ))
          .build());
    }

    @Test
    void givenUserId_whenGetOrdersByUserId_thenReturnAllUserOrders() {
        List<Map<String, AttributeValue>> items = repository.getOrdersByUserId("user1");
        assertEquals(4, items.size());
    }

    @Test
    void givenStartDate_whenGetOrdersAfterDate_thenReturnOnlyNewerOrders() {
        List<Map<String, AttributeValue>> items = repository.getOrdersAfterDate("user1", "2025-01-01");
        List<String> names = items.stream().map(i -> i.get("item").s()).collect(Collectors.toList());

        assertEquals(List.of("Mouse", "Keyboard"), names);
    }

    @Test
    void givenDateRange_whenGetOrdersBetweenDates_thenReturnOrdersInRange() {
        List<Map<String, AttributeValue>> items = repository.getOrdersBetweenDates("user1", "2024-12-01", "2024-12-31");
        List<String> names = items.stream().map(i -> i.get("item").s()).collect(Collectors.toList());

        assertEquals(List.of("Laptop", "Monitor"), names);
    }

    @Test
    void givenMonthPrefix_whenGetOrdersByMonth_thenReturnMonthlyOrders() {
        List<Map<String, AttributeValue>> items = repository.getOrdersByMonth("user1", "2025-01");
        List<String> names = items.stream().map(i -> i.get("item").s()).collect(Collectors.toList());

        assertEquals(List.of("Mouse", "Keyboard"), names);
    }

    @Test
    void givenUserId_whenGetAllOrdersPaginated_thenReturnAllUserOrders() {
        List<Map<String, AttributeValue>> items = repository.getAllOrdersPaginated("user1");

        assertEquals(4, items.size());

        List<String> itemNames = items.stream()
          .map(item -> item.get("item").s())
          .collect(Collectors.toList());

        assertTrue(itemNames.containsAll(List.of("Laptop", "Monitor", "Mouse", "Keyboard")));
    }

    @AfterAll
    void tearDown() {
        if (localstack != null) {
            localstack.stop();
        }
    }
}