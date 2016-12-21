package com.baeldung.sparkjava;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class UserStore {
    private static HashMap<String, User> userMap;
    
    static {
        userMap = new HashMap<>();
    }
    
    public static void addUser (User user) {
        userMap.put(user.getId(), user);
    }
    
    public static Collection<User> getUsers () {
        return  userMap.values();
    }
    
    public static User getUser (String id) {
        return userMap.get(id);
    }
    
    public static User editUser (String id, HashMap userArg) throws Exception{
        User toEdit = userMap.get(id);
        if (toEdit == null )
            return null;
        toEdit.setEmail((userArg.get("email")!=null) ? (String) userArg.get("email") : toEdit.getEmail() );
        toEdit.setFirstName((userArg.get("firstName")!=null) ? (String) userArg.get("firstName") : toEdit.getFirstName());
        toEdit.setLastName((userArg.get("lastName")!=null) ? (String) userArg.get("lastName") : toEdit.getLastName());
        toEdit.setId((userArg.get("id")!=null) ? String.valueOf (userArg.get("id")) : toEdit.getId() );
        return toEdit;
    }
    public static void deleteUser (String id) {
        userMap.remove(id);
    }
    
    public static boolean userExist (String id) {
        return userMap.containsKey(id);
    }

}
