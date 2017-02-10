/**
 * "Unpublished Work © 2017 Deere & Company. All Worldwide Rights Reserved. THIS MATERIAL IS THE PROPERTY OF DEERE &
 * COMPANY. ALL USE, ALTERATIONS, DISCLOSURE, DISSEMINATION AND/OR REPRODUCTION NOT SPECIFICALLY AUTHORIZED BY DEERE &
 * COMPANY IS PROHIBITED."
 */
package com.baeldung.lambda.dynamodb;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.baeldung.lambda.dynamodb.bean.PersonRequest;
import com.baeldung.lambda.dynamodb.bean.PersonResponse;

public class SavePersonHandler implements RequestHandler<PersonRequest, PersonResponse> {

    private DynamoDB dynamoDb;

    private final String DYNAMODB_TABLE_NAME = "Person";
    private final Regions REGION = Regions.US_WEST_2;

    public PersonResponse handleRequest(PersonRequest personRequest, Context context) {
        context.getLogger().log("personRequest: " + personRequest);
        this.initDynamoDbClient();

        persistData(personRequest);

        PersonResponse personResponse = new PersonResponse();
        personResponse.setMessage("Saved Successfully!!!");
        context.getLogger().log("personResponse: " + personResponse);
        return personResponse;
    }

    private PutItemOutcome persistData(PersonRequest personRequest) throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                            .withNumber("id", personRequest.getId())
                                            .withString("firstName", personRequest.getFirstName())
                                            .withString("lastName", personRequest.getLastName())
                                            .withNumber("age", personRequest.getAge())
                                            .withString("address", personRequest.getAddress())
                        ));
    }

    private void initDynamoDbClient() {
        final AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }
}
