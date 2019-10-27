package com.baeldung.spring.cloud.archaius.dynamosources.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@DynamoDBTable(tableName = "archaiusProperties")
public class ArchaiusProperties {

    @DynamoDBHashKey
    @DynamoDBAttribute
    private String key;

    @DynamoDBAttribute
    private String value;
}
