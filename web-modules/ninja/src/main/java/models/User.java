package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;
    
    @NotNull
    public String firstName;

    public String email;
    
    public String toString() {
        return firstName + " : " + email;
    }
    
}
