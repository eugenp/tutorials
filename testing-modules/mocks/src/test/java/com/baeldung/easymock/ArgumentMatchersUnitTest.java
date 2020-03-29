package com.baeldung.easymock;

import static org.easymock.EasyMock.and;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.contains;
import static org.easymock.EasyMock.endsWith;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.geq;
import static org.easymock.EasyMock.gt;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.lt;
import static org.easymock.EasyMock.matches;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.not;
import static org.easymock.EasyMock.notNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.startsWith;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.junit.Test;

public class ArgumentMatchersUnitTest {

    private IUserService userService = mock(IUserService.class);

    //====================== equals
    @Test
    public void givenUserService_whenAddNewUser_thenOK() {        
        expect(userService.addUser(eq(new User()))).andReturn(true);
        replay(userService);

        boolean result = userService.addUser(new User());
        verify(userService);
        assertTrue(result);
    }      
    
    //================ same
    @Test
    public void givenUserService_whenAddSpecificUser_thenOK() {
        User user = new User();
        
        expect(userService.addUser(same(user))).andReturn(true);
        replay(userService);

        boolean result = userService.addUser(user);
        verify(userService);
        assertTrue(result);
    } 
    
    //============= anyX
    @Test
    public void givenUserService_whenSearchForUserByEmail_thenFound() {        
        expect(userService.findByEmail(anyString())).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByEmail("test@example.com");
        verify(userService);
        assertEquals(0,result.size());
    }    
    
    //================= isA
    @Test
    public void givenUserService_whenAddUser_thenOK() {        
        expect(userService.addUser(isA(User.class))).andReturn(true);
        replay(userService);

        boolean result = userService.addUser(new User());
        verify(userService);
        assertTrue(result);
    }    
        
    //=================== null, not null
    @Test
    public void givenUserService_whenAddNull_thenFail() {        
        expect(userService.addUser(isNull())).andReturn(false);
        replay(userService);

        boolean result = userService.addUser(null);
        verify(userService);
        assertFalse(result);
    }      
    
    @Test
    public void givenUserService_whenAddNotNull_thenOK() {        
        expect(userService.addUser(notNull())).andReturn(true);
        replay(userService);

        boolean result = userService.addUser(new User());
        verify(userService);
        assertTrue(result);
    }  
    
    // number less,great
    @Test
    public void givenUserService_whenSearchForUserByAgeLessThan_thenFound() {        
        expect(userService.findByAge(lt(100.0))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByAge(20);        
        verify(userService);
        assertEquals(0,result.size());
    }    
    
    @Test
    public void givenUserService_whenSearchForUserByAgeGreaterThan_thenFound() {        
        expect(userService.findByAge(geq(10.0))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByAge(20);        
        verify(userService);
        assertEquals(0,result.size());
    }    
    
    //=============== string 
    //=============== start
    @Test
    public void givenUserService_whenSearchForUserByEmailStartsWith_thenFound() {        
        expect(userService.findByEmail(startsWith("test"))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByEmail("test@example.com");
        verify(userService);
        assertEquals(0,result.size());
    }
    
    //==================end
    @Test
    public void givenUserService_whenSearchForUserByEmailEndsWith_thenFound() {        
        expect(userService.findByEmail(endsWith(".com"))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByEmail("test@example.com");
        verify(userService);
        assertEquals(0,result.size());
    }
    
    //=================contain
    @Test
    public void givenUserService_whenSearchForUserByEmailContains_thenFound() {        
        expect(userService.findByEmail(contains("@"))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByEmail("test@example.com");
        verify(userService);
        assertEquals(0,result.size());
    }
    
    //==================matches
    @Test
    public void givenUserService_whenSearchForUserByEmailMatches_thenFound() {        
        expect(userService.findByEmail(matches(".+\\@.+\\..+"))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByEmail("test@example.com");
        verify(userService);
        assertEquals(0,result.size());
    }
    
    //================== combine and, or, not
    @Test
    public void givenUserService_whenSearchForUserByAgeRange_thenFound() {        
        expect(userService.findByAge(and(gt(10.0),lt(100.0)))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByAge(20);        
        verify(userService);
        assertEquals(0,result.size());
    }        
    
    @Test
    public void givenUserService_whenSearchForUserByEmailNotEndsWith_thenFound() {        
        expect(userService.findByEmail(not(endsWith(".com")))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByEmail("test@example.org");
        verify(userService);
        assertEquals(0,result.size());
    }    
    
    //================ custom matcher
    
    @Test
    public void givenUserService_whenSearchForUserByEmailCharCount_thenFound() {        
        expect(userService.findByEmail(minCharCount(5))).andReturn(Collections.emptyList());
        replay(userService);

        List<User> result = userService.findByEmail("test@example.com");
        verify(userService);
        assertEquals(0,result.size());
    }
    
    public static String minCharCount(int value){
        EasyMock.reportMatcher(new IArgumentMatcher() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof String 
                  && ((String) argument).length() >= value;
            }
     
            @Override
            public void appendTo(StringBuffer buffer) {
                buffer.append("charCount(\"" + value + "\")");
            }
        });    
        return null;
    }
}


