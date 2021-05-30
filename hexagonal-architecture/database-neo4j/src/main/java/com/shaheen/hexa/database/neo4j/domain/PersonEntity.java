package main.java.com.shaheen.hexa.database.neo4j.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class PersonEntity {
    private Long id;
    private String name;
    private String firstName;
    private Integer age;

    public PersonEntity(final String name, final String firstName, final Integer age) {
        this.name = name;
        this.firstName = firstName;
        this.age = age;
    }
}
