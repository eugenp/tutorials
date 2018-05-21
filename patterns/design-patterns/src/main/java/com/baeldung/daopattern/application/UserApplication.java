package com.baeldung.daopattern.application;

import com.baeldung.daopattern.daos.Dao;
import com.baeldung.daopattern.daos.JpaUserDao;
import com.baeldung.daopattern.entities.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class UserApplication {
    
    private static JpaUserDao jpaUserDao;
    
    public static void main(String[] args) {
        User user1 = getUser(1);
        System.out.println(user1);
        updateUser(user1, new String[]{"John", "john@domain.com"});
        saveUser(new User("Monica", "monica@domain.com"));
        deleteUser(getUser(2));
        getAllUsers().forEach(user -> System.out.println(user.getName()));
    }
    
    public static Dao getJpaUserDao() {
        if (jpaUserDao == null) {
            EntityManager entityManager = Persistence.createEntityManagerFactory("user-unit").createEntityManager();
            jpaUserDao = new JpaUserDao(entityManager);
        }
        return jpaUserDao;
    }
    
    public static User getUser(long id) {
        Optional<User> user = getJpaUserDao().get(id);
        return user.orElse(new User("Non-existing user", "no-email"));
    }
    
    public static List<User> getAllUsers() {
        return getJpaUserDao().getAll();
    }
    
    public static void updateUser(User user, String[] params){
        getJpaUserDao().update(user, params);
    }
    
    public static void saveUser(User user) {
        getJpaUserDao().save(user);
    }
    
    public static void deleteUser(User user) {
        getJpaUserDao().delete(user);
    }
}
