package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.beaninjectiontypes.models.Family;

@Component
public class SetterInjectionBean {

    private Family family;

    @Autowired
    public void setFamily(Family family) {
        this.family = family;
    }

    public int noOfChildren() {
        return family.getChildren()
            .size();
    }

    public void addChildToFamily(String childName) {
        family.getChildren()
            .add(childName);
    }

    public boolean isChildAlive(String childName) {
        return family.getChildren()
            .contains(childName);
    }

    public void killChild(String childName) {
        family.getChildren()
            .remove(childName);
    }

}
