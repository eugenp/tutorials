
package com.baeldung.portsandadaptors.core.model;

import java.io.Serializable;

/**
 *
 * @author DeependraTewari
 */
public class User implements Serializable {
    
    private Long id;
    
    private String firstName;
    private String middleName;
    private String lastName;
        
    private String twitterHandle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }
    
}
