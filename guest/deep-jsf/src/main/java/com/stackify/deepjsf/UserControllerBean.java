package com.stackify.deepjsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class UserControllerBean {

    public String register() {
        return "register-success";
    }

}
