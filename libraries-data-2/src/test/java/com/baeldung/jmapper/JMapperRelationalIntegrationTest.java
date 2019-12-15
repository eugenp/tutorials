package com.baeldung.jmapper;

import com.baeldung.jmapper.relational.User;
import com.baeldung.jmapper.relational.UserDto1;
import com.baeldung.jmapper.relational.UserDto2;
import com.googlecode.jmapper.RelationalJMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import org.junit.Test;

import static com.googlecode.jmapper.api.JMapperAPI.attribute;
import static com.googlecode.jmapper.api.JMapperAPI.mappedClass;
import static org.junit.Assert.assertEquals;

public class JMapperRelationalIntegrationTest {

    
    @Test
    public void givenUser_whenUseAnnotation_thenConverted(){
        RelationalJMapper<User> relationalMapper = new RelationalJMapper<>(User.class);
        
        User user = new User(1L,"john@test.com");
        UserDto1 result1 = relationalMapper.oneToMany(UserDto1.class, user);
        UserDto2 result2= relationalMapper.oneToMany(UserDto2.class, user);

        System.out.println(result1);
        System.out.println(result2);
        assertEquals(user.getId(), result1.getId());
        assertEquals(user.getEmail(), result1.getUsername());  
        assertEquals(user.getId(), result2.getId());
        assertEquals(user.getEmail(), result2.getEmail());          
    }     

    //======================= XML
    
    @Test
    public void givenUser_whenUseXml_thenConverted(){
        RelationalJMapper<User> relationalMapper = new RelationalJMapper<>(User.class,"user_jmapper2.xml");
        
        User user = new User(1L,"john@test.com");
        UserDto1 result1 = relationalMapper.oneToMany(UserDto1.class, user);
        UserDto2 result2 = relationalMapper.oneToMany(UserDto2.class, user);

        System.out.println(result1);
        System.out.println(result2);
        assertEquals(user.getId(), result1.getId());
        assertEquals(user.getEmail(), result1.getUsername());
        assertEquals(user.getId(), result2.getId());
        assertEquals(user.getEmail(), result2.getEmail());         
    }
    
    
    // ===== API
    
    @Test
    public void givenUser_whenUseApi_thenConverted(){
        JMapperAPI jmapperApi = new JMapperAPI() 
        .add(mappedClass(User.class)
            .add(attribute("id").value("id").targetClasses(UserDto1.class,UserDto2.class))
            .add(attribute("email").targetAttributes("username","email").targetClasses(UserDto1.class,UserDto2.class)) )
         ;
        RelationalJMapper<User> relationalMapper = new RelationalJMapper<>(User.class,jmapperApi);
        
        User user = new User(1L,"john@test.com");
        UserDto1 result1 = relationalMapper.oneToMany(UserDto1.class, user);
        UserDto2 result2 = relationalMapper.oneToMany(UserDto2.class, user);

        System.out.println(result1);
        System.out.println(result2);
        assertEquals(user.getId(), result1.getId());
        assertEquals(user.getEmail(), result1.getUsername());
        assertEquals(user.getId(), result2.getId());
        assertEquals(user.getEmail(), result2.getEmail());            
    }

}
