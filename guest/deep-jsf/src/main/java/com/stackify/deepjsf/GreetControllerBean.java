package com.stackify.deepjsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class GreetControllerBean {

    public String greet() {
        return "greet";
    }

}
