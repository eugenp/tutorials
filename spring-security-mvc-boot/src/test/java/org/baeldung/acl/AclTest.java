package org.baeldung.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.baeldung.acl.persistence.dao.NoticeMessageRepository;
import org.baeldung.acl.persistence.entity.NoticeMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class AclTest extends AbstractJUnit4SpringContextTests{
    
    private static Integer FIRST_MESSAGE_ID = 1;
    private static Integer SECOND_MESSAGE_ID = 2;
    private static Integer THIRD_MESSAGE_ID = 3;
    private static String EDITTED_CONTENT = "EDITED";
    
    @Configuration
    @ComponentScan("org.baeldung.acl.*")
    public static class SpringConfig {

    }
    
    @Autowired
    NoticeMessageRepository repo;
    
    /**
     * User manager
     * Situation: READ/WRITE permissions are granted only on ChatMessage with id 1
     */
    @Test
    @WithMockUser(username="manager")
    public void testUserManagerPermission() {
        List<NoticeMessage> details = repo.findAll();
        
        assertNotNull(details);
        assertEquals(1,details.size());
        assertEquals(FIRST_MESSAGE_ID,details.get(0).getId());
        
        NoticeMessage firstMessage = repo.findById(FIRST_MESSAGE_ID);
        assertNotNull(firstMessage);
        assertEquals(FIRST_MESSAGE_ID,firstMessage.getId());
        
        firstMessage.setContent(EDITTED_CONTENT);
        repo.save(firstMessage);
        
        NoticeMessage editedFirstMessage = repo.findById(FIRST_MESSAGE_ID);
        assertNotNull(editedFirstMessage);
        assertEquals(FIRST_MESSAGE_ID,editedFirstMessage.getId());
        assertEquals(EDITTED_CONTENT,editedFirstMessage.getContent());
    }
    
    /**
     * User hr
     * Situation: READ permissions is granted only on ChatMessage with id 2
     */
    @Test
    @WithMockUser(username="hr")
    public void testUserHrPermission() {
        List<NoticeMessage> details = repo.findAll();
        
        assertNotNull(details);
        assertEquals(1,details.size());
        assertEquals(SECOND_MESSAGE_ID,details.get(0).getId());
        
        NoticeMessage secondMessage = repo.findById(SECOND_MESSAGE_ID);
        assertNotNull(secondMessage);
        assertEquals(SECOND_MESSAGE_ID,secondMessage.getId());
        
        secondMessage.setContent(EDITTED_CONTENT);
        Exception writeDenied = null;
        try{
            repo.save(secondMessage);
        } catch (Exception e) {
            writeDenied = e;
        }
        
        assertNotNull(writeDenied);
        assertTrue(writeDenied instanceof AccessDeniedException);
                
        NoticeMessage editedSecondMessage = repo.findById(SECOND_MESSAGE_ID);
        assertNotNull(editedSecondMessage);
        assertEquals(SECOND_MESSAGE_ID,editedSecondMessage.getId());
        assertNotEquals(EDITTED_CONTENT,editedSecondMessage.getContent());
    }
    
    /**
     * User employee
     * Situation: no permission on any object
     */
    @Test
    @WithMockUser(username="employee")
    public void testUserEmployeePermission() {
        List<NoticeMessage> details = repo.findAll();
        
        assertNotNull(details);
        assertEquals(0,details.size());
        
        Exception readDenied = null;
        try{
            repo.findById(THIRD_MESSAGE_ID);
        } catch (Exception e) {
            readDenied = e;
        }
        
        assertNotNull(readDenied);
        assertTrue(readDenied instanceof AccessDeniedException);
       
        Exception writeDenied = null;
        try{
            NoticeMessage thirdMessage = new NoticeMessage();
            thirdMessage.setId(THIRD_MESSAGE_ID);
            repo.save(thirdMessage);
        } catch (Exception e) {
            writeDenied = e;
        }
        
        assertNotNull(writeDenied);
        assertTrue(writeDenied instanceof AccessDeniedException);
                
    }
    
    /**
     * User manager
     * Situation: READ/WRITE permissions are granted only on ChatMessage with id 1
     */
    @Test
    @WithMockUser(username="test",roles={"EDITOR"})
    public void testRoleEditorPermission() {
        List<NoticeMessage> details = repo.findAll();
        
        assertNotNull(details);
        assertEquals(3,details.size());
        
        NoticeMessage thirdMessage = details.get(2);
        assertNotNull(thirdMessage);
        assertEquals(THIRD_MESSAGE_ID,thirdMessage.getId());
        
        thirdMessage.setContent(EDITTED_CONTENT);
        repo.save(thirdMessage);
        
        NoticeMessage editedThirdMessage = repo.findById(THIRD_MESSAGE_ID);
        assertNotNull(editedThirdMessage);
        assertEquals(THIRD_MESSAGE_ID,editedThirdMessage.getId());
        assertEquals(EDITTED_CONTENT,editedThirdMessage.getContent());
        
        Exception writeDenied = null;
        try{
            NoticeMessage firstMessage = details.get(0);
            firstMessage.setContent(EDITTED_CONTENT);
            repo.save(firstMessage);
        } catch (Exception e) {
            writeDenied = e;
        }
        
        assertNotNull(writeDenied);
        assertTrue(writeDenied instanceof AccessDeniedException);
    }
}
    