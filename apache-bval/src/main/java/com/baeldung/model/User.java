package com.baeldung.model;

import java.io.File;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;
import org.apache.bval.extras.constraints.checkdigit.IBAN;
import org.apache.bval.extras.constraints.creditcard.Visa;
import org.apache.bval.extras.constraints.file.Directory;
import org.apache.bval.extras.constraints.net.InetAddress;

import com.baeldung.validation.Password;

public class User {
    @NotNull
    @Email
    private String email;

    @NotEmpty
    @Password
    private String password;

    @Size(min = 1, max = 20)
    private String name;

    @Min(18)
    private int age;

    @Visa
    private String cardNumber = "";

    @IBAN
    private String iban = "";

    @InetAddress
    private String website = "";

    @Directory
    private File mainDirectory=new File(".");

    public User() {
    }

    public User(String email, String password, String name, int age) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public File getMainDirectory() {
        return mainDirectory;
    }

    public void setMainDirectory(File mainDirectory) {
        this.mainDirectory = mainDirectory;
    }

}
