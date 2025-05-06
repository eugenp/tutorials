package com.baeldung.spring.cloud.aws.dynamodb;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import net.bytebuddy.utility.RandomString;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class UserCRUDLiveTest {

    @Autowired
    private DynamoDbTemplate dynamoDbTemplate;

    @Test
    void whenUserSaved_thenItemCreatedInDynamoDB() {
        User user = Instancio.create(User.class);

        dynamoDbTemplate.save(user);

        Key partitionKey = Key.builder().partitionValue(user.getId().toString()).build();
        User retrievedUser = dynamoDbTemplate.load(partitionKey, User.class);
        assertThat(retrievedUser)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(user);
    }

    @Test
    void whenUserUpdated_thenItemUpdatedInDynamoDB() {
        User user = Instancio.create(User.class);
        dynamoDbTemplate.save(user);

        String updatedName = RandomString.make();
        String updatedEmail = RandomString.make();
        user.setName(updatedName);
        user.setEmail(updatedEmail);
        dynamoDbTemplate.update(user);

        Key partitionKey = Key.builder().partitionValue(user.getId().toString()).build();
        User updatedUser = dynamoDbTemplate.load(partitionKey, User.class);
        assertThat(updatedUser)
            .isNotNull();
        assertThat(updatedUser.getName())
            .isEqualTo(updatedName);
        assertThat(updatedUser.getEmail())
            .isEqualTo(updatedEmail);
    }

    @Test
    void whenUserDeleted_thenItemRemovedFromDynamoDB() {
        User user = Instancio.create(User.class);
        dynamoDbTemplate.save(user);

        dynamoDbTemplate.delete(user);

        Key partitionKey = Key.builder().partitionValue(user.getId().toString()).build();
        User deletedUser = dynamoDbTemplate.load(partitionKey, User.class);
        assertThat(deletedUser)
            .isNull();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void whenUserEntityScanned_thenAllSavedItemsReturned() {
        int numberOfUsers = 10;
        for (int i = 0; i < numberOfUsers; i++) {
            User user = Instancio.create(User.class);
            dynamoDbTemplate.save(user);
        }

        List<User> retrievedUsers = dynamoDbTemplate
            .scanAll(User.class)
            .items()
            .stream()
            .toList();

        assertThat(retrievedUsers.size())
            .isEqualTo(numberOfUsers);
    }

    @Test
    void whenUserQueriedByEmail_thenCorrectItemReturned() {
        User user = Instancio.create(User.class);
        dynamoDbTemplate.save(user);

        Expression expression = Expression.builder()
            .expression("#email = :email")
            .putExpressionName("#email", "email")
            .putExpressionValue(":email", AttributeValue.builder().s(user.getEmail()).build())
            .build();
        ScanEnhancedRequest scanRequest = ScanEnhancedRequest
            .builder()
            .filterExpression(expression)
            .build();
        User retrievedUser = dynamoDbTemplate.scan(scanRequest, User.class)
            .items()
            .stream()
            .findFirst()
            .get();

        assertThat(retrievedUser)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(user);
    }

}