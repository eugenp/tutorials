package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Student {
    private String firstName;
    private String lastName;
    private int age;
    private int id;

    public Student(@JsonProperty(value = "firstName") String firstName,
                   @JsonProperty(value = "lastName") String lastName,
                   @JsonProperty(value = "age") int age,
                   @JsonProperty(value = "id") int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.id = id;
    }
}
