package com.stackify.deepjsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@SessionScoped
public class UserBean {

    private String name = "";

    private String lastName = "";

    private String proposedLogin = "";

    public void nameChanged(ValueChangeEvent event) {
        this.proposedLogin = event.getNewValue() + "-" + lastName;
    }

    public void lastNameChanged(ValueChangeEvent event) {
        this.proposedLogin = name + "-" + event.getNewValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProposedLogin() {
        return proposedLogin;
    }

    public void setProposedLogin(String proposedLogin) {
        this.proposedLogin = proposedLogin;
    }
}
