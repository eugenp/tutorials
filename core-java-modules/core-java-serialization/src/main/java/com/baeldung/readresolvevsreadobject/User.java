package com.baeldung.readresolvevsreadobject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 3659932210257138726L;
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", password=" + password + "]";
    }

    public User() {
    }

    public User(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        this.password = "xyz" + password;
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream aInputStream)
            throws ClassNotFoundException, IOException {
        aInputStream.defaultReadObject();
        this.password = password.substring(3);
    }

    private Object readResolve() {
        return this;
    }

}
