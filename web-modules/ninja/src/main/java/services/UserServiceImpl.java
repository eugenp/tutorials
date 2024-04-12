package services;

import java.util.HashMap;

public class UserServiceImpl implements UserService {

    @Override
    public HashMap<String, String> getUserMap() {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", "Norman Lewis");
        userMap.put("email", "norman@email.com");
        return userMap;
    }

}
