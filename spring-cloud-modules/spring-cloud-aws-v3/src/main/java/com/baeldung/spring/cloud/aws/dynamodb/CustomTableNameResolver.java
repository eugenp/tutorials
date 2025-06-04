package com.baeldung.spring.cloud.aws.dynamodb;

import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import org.springframework.stereotype.Component;

@Component
class CustomTableNameResolver implements DynamoDbTableNameResolver {
    
    @Override
    public <T> String resolve(Class<T> clazz) {
        return clazz.getAnnotation(TableName.class).name();
    }
    
}