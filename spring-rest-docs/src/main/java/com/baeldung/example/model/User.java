package com.baeldung.example.model;

import org.springframework.hateoas.ResourceSupport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User extends ResourceSupport {

    private static final String DAT_FORMAT = "yyyy-MM-dd";

    private String name;
    private String tld;
    private Date expirationDate;
    private boolean autorenewal;

    public User() {
    }

    public User(String name, String tld, String expirationDate, boolean autorenewal) {
        this.name = name;
        this.tld = tld;
        this.expirationDate = parseDate(expirationDate);
        this.autorenewal = autorenewal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    private Date parseDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DAT_FORMAT);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("The expirationDate " + date + "could not be parsed");
        }
    }

    public boolean getAutorenewal() {
        return autorenewal;
    }

    public void setAutorenewal(boolean autorenewal) {
        this.autorenewal = autorenewal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (autorenewal != user.autorenewal) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (tld != null ? !tld.equals(user.tld) : user.tld != null) return false;
        return !(expirationDate != null ? !expirationDate.equals(user.expirationDate) : user.expirationDate != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tld != null ? tld.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (autorenewal ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", tld='" + tld + '\'' +
                ", expirationDate=" + expirationDate +
                ", autorenewal=" + autorenewal +
                '}';
    }
}
