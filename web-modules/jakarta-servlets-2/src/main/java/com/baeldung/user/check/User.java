package com.baeldung.user.check;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @since 6 de fev de 2022
 * @author ulisses
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected static final HashMap<String, User> DB = new HashMap<>();
    static {
        DB.put("admin", new User("admin", "password"));
        DB.put("user", new User("user", "pass"));
    }

    private String name;
    private String password;

    private List<Date> logins = new ArrayList<Date>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Date> getLogins() {
        return logins;
    }

    public void setLogins(List<Date> logins) {
        this.logins = logins;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
