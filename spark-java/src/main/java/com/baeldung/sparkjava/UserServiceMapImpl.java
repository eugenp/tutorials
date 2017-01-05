package com.baeldung.sparkjava;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceMapImpl  implements UserService{
    private HashMap<String, User> userMap;
    
    public UserServiceMapImpl() {
        userMap = new HashMap<>();
    }

    public void addUser (User user) {
        userMap.put(user.getId(), user);
    }
    
    public Collection<User> getUsers () {
        return  userMap.values();
    }
    
    public User getUser (String id) {
        return userMap.get(id);
    }
    
    public User editUser (String id, Map userArg) throws UserException{
        try{
            User toEdit = userMap.get(id);
            if (toEdit == null )
                return null;
            
            if (userArg.get("email")!=null) {
                toEdit.setEmail((String) userArg.get("email"));
            }
            if (userArg.get("firstName")!=null) {
                toEdit.setFirstName((String) userArg.get("firstName"));
            }
            if (userArg.get("lastName")!=null) {
                toEdit.setLastName((String) userArg.get("lastName"));
            }
            if (userArg.get("id")!=null) {
                toEdit.setId(String.valueOf (userArg.get("id")));
            }

            return toEdit;
        }catch (Exception ex) {
            throw new UserException(ex.getMessage());
        }
    }
    public void deleteUser (String id) {
        userMap.remove(id);
    }
    
    public boolean userExist (String id) {
        return userMap.containsKey(id);
    }

}
