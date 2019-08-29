package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private String firstName;
    private String lastName;
    private int age;
    private int id;
}
