package com.baeldung.spring.data.es.termsqueries.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "users")
public class User {
    @Id
    private String id;
    @Field(type = FieldType.Keyword, name = "role")
    private String role;
    @Field(type = FieldType.Text, name = "name")
    private String name;
    @Field(type = FieldType.Boolean, name = "is_active")
    private Boolean isActive;
}
