package com.baeldung.dynamodb.query;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;

public class UserOrdersRepository {

    private final DynamoDbClient dynamoDb;
    private static final String TABLE_NAME = "UserOrders";

    public UserOrdersRepository(DynamoDbClient dynamoDb) {
        this.dynamoDb = dynamoDb;
    }

    public List<Map<String, AttributeValue>> getOrdersByUserId(String userId) {
        QueryRequest request = QueryRequest.builder()
          .tableName(TABLE_NAME)
          .keyConditionExpression("userId = :uid")
          .expressionAttributeValues(Map.of(
            ":uid", AttributeValue.fromS(userId)
          ))
          .build();

        return dynamoDb.query(request).items();
    }

    public List<Map<String, AttributeValue>> getOrdersAfterDate(String userId, String startDate) {
        QueryRequest request = QueryRequest.builder()
          .tableName(TABLE_NAME)
          .keyConditionExpression("userId = :uid AND orderDate > :startDate")
          .expressionAttributeValues(Map.of(
            ":uid", AttributeValue.fromS(userId),
            ":startDate", AttributeValue.fromS(startDate)
          ))
          .build();

        return dynamoDb.query(request).items();
    }

    public List<Map<String, AttributeValue>> getOrdersBetweenDates(String userId, String fromDate, String toDate) {
        QueryRequest request = QueryRequest.builder()
          .tableName(TABLE_NAME)
          .keyConditionExpression("userId = :uid AND orderDate BETWEEN :from AND :to")
          .expressionAttributeValues(Map.of(
            ":uid", AttributeValue.fromS(userId),
            ":from", AttributeValue.fromS(fromDate),
            ":to", AttributeValue.fromS(toDate)
          ))
          .build();

        return dynamoDb.query(request).items();
    }

    public List<Map<String, AttributeValue>> getOrdersByMonth(String userId, String monthPrefix) {
        QueryRequest request = QueryRequest.builder()
          .tableName(TABLE_NAME)
          .keyConditionExpression("userId = :uid AND begins_with(orderDate, :prefix)")
          .expressionAttributeValues(Map.of(
            ":uid", AttributeValue.fromS(userId),
            ":prefix", AttributeValue.fromS(monthPrefix)
          ))
          .build();

        return dynamoDb.query(request).items();
    }
}
