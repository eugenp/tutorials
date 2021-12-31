package com.baeldung.hexagonal.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagonal.dto.User;

public class FileBasedUserRepository implements UserRepository {

    @Override
    public List<User> getUsers() {
        String line = "";  
        List<User> userList = new ArrayList<>();
        try   
        {  
            //parsing a CSV file into BufferedReader class constructor  
            BufferedReader br = new BufferedReader(new FileReader("users.csv"));  
            while ((line = br.readLine()) != null)   //returns a Boolean value  
            {  
                String[] userComponents = line.split(",");    // use comma as separator  
                User user = new User();
                user.setId(Integer.valueOf(userComponents[0]));
                user.setFirstName(userComponents[1]);
                user.setLastName(userComponents[2]);
                userList.add(user);
            }  
            br.close();
            return userList;
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        return null;
    }
}
