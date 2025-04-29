package com.baeldung.spring.cloud.aws.dynamodb;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import net.bytebuddy.utility.RandomString;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class UserCRUDLiveTest {

    @Autowired
    private DynamoDbTemplate dynamoDbTemplate;

}