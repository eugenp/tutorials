package com.baeldung.eclipse.krazo;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.mvc.RedirectScoped;
import jakarta.mvc.binding.MvcBinding;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;

import java.io.Serializable;

@Named("user")
@RedirectScoped
public class User implements Serializable {
    @MvcBinding
    @Null
    private String id;

    @MvcBinding
    @NotNull
    @Size(min = 1, message = "Name cannot be blank")
    @FormParam("name")
    private String name;

    @MvcBinding
    @Min(value = 18, message = "The minimum age of the user should be 18 years")
    @FormParam("age")
    private int age;

    @MvcBinding
    @Email(message = "The email cannot be blank and should be in a valid format")
    @Size(min=3, message = "Email cannot be empty")
    @FormParam("email")
    private String email;

    @MvcBinding
    @Null
    @FormParam("phone")
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
