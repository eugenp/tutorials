package org.baeldung.domain;

public class UserVO {

    private String name, pwd;

    public UserVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return String.format("User [%s, %s]", name, pwd);
    }
}
