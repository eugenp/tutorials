package org.baeldung.dao;

import org.baeldung.dto.User;

import java.util.List;

public interface UserDao{
   void create(User user);
   void update(User user);
   void delete(User user);

   List<String> getAllUserNames();

   List<User> findAll();

   User findByPrimaryKey(String group, String company, String fullname);
}
