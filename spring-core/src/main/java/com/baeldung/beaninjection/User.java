package com.baeldung.beaninjection;

@Component
public class User {

    private Property property;

    public User(){
    }

    @Autowired
    public void setProperty(Property property) {
        this.property = property;
    }
}
