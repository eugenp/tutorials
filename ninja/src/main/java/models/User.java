package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;
    
    public String firstName;
    public String email;
    
    public String toString() {
        return firstName + " : " + email;
    }
    
}
