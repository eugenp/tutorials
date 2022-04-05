package com.baeldung.springvalidation.domain;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.baeldung.springvalidation.interfaces.AdvanceInfo;
import com.baeldung.springvalidation.interfaces.BasicInfo;

public class UserAccount {

    @NotNull(groups = BasicInfo.class)
    @Size(min = 4, max = 15, groups = BasicInfo.class)
    private String password;

    @NotBlank(groups = BasicInfo.class)
    private String name;

    @Min(value = 18, message = "Age should not be less than 18", groups = AdvanceInfo.class)
    private int age;

    @NotBlank(groups = AdvanceInfo.class)
    private String phone;

    @Valid
    @NotNull(groups = AdvanceInfo.class)
    private UserAddress useraddress;

    public UserAddress getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(UserAddress useraddress) {
        this.useraddress = useraddress;
    }

    public UserAccount() {

    }

    public UserAccount(String email, String password, String name, int age) {
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
