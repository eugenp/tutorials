package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShallowVsDeepCopy {

    @Test
    void testReferenceCopyWhenCopyIsModifiedOriginalShouldAlsoBeMofidified() {
        String originalEmail = "original@email.com";
        String modifiedEmail = "modified@email.com";
        String orignalGsmNo = "0000000000";
        String modifiedGsmNo = "1111111111";
        
        ContactInfo original = new ContactInfo();
        original.setEmail(originalEmail);
        original.setGsmNo(orignalGsmNo);
        
        ContactInfo copy = original;
        copy.setEmail(modifiedEmail);
        copy.setGsmNo(modifiedGsmNo);
        
        assertEquals(modifiedEmail, original.getEmail());
        assertEquals(modifiedGsmNo, original.getGsmNo());
    }
    
    @Test
    void testShallowCopyWithoutNestedClassesWhenCopyIsModifiedOriginalShouldNotBeMofidified() {
        String originalEmail = "original@email.com";
        String modifiedEmail = "modified@email.com";
        String orignalGsmNo = "0000000000";
        String modifiedGsmNo = "1111111111";
        
        ContactInfo original = new ContactInfo();
        original.setEmail(originalEmail);
        original.setGsmNo(orignalGsmNo);
        
        ContactInfo copy = original.makeACopy();
        copy.setEmail(modifiedEmail);
        copy.setGsmNo(modifiedGsmNo);
        
        assertNotEquals(modifiedEmail, original.getEmail());
        assertNotEquals(modifiedGsmNo, original.getGsmNo());
    }
    
    @Test
    void testShallowCopyWithNestedClassesWhenCopysNestedObjectIsModifiedOriginalShouldAlsoBeMofidified() {
        String originalFirst = "John";
        String modifiedFirst = "Jack";
        String originalLast = "Doe";
        String modifiedLast = "Moe";
        String originalEmail = "original@email.com";
        String modifiedEmail = "modified@email.com";
        String orignalGsmNo = "0000000000";
        String modifiedGsmNo = "1111111111";
        
        Customer original = new Customer();
        original.setFirst(originalFirst);
        original.setLast(originalLast);
        ContactInfo originalContact = new ContactInfo();
        originalContact.setEmail(originalEmail);
        originalContact.setGsmNo(orignalGsmNo);
        original.setContactInfo(originalContact);
        
        Customer copy = original.makeAShallowCopy();
        copy.setFirst(modifiedFirst);
        copy.setLast(modifiedLast);
        copy.getContactInfo().setEmail(modifiedEmail);
        copy.getContactInfo().setGsmNo(modifiedGsmNo);
        
        assertNotEquals(modifiedFirst, original.getFirst());
        assertNotEquals(modifiedLast, original.getLast());
        assertEquals(modifiedEmail, original.getContactInfo().getEmail());
        assertEquals(modifiedGsmNo, original.getContactInfo().getGsmNo());
    }
    
    @Test
    void testDeepCopyWithNestedClassesWhenCopysNestedObjectIsModifiedOriginalShouldNotBeMofidified() {
        String originalFirst = "John";
        String modifiedFirst = "Jack";
        String originalLast = "Doe";
        String modifiedLast = "Moe";
        String originalEmail = "original@email.com";
        String modifiedEmail = "modified@email.com";
        String orignalGsmNo = "0000000000";
        String modifiedGsmNo = "1111111111";
        
        Customer original = new Customer();
        original.setFirst(originalFirst);
        original.setLast(originalLast);
        ContactInfo originalContact = new ContactInfo();
        originalContact.setEmail(originalEmail);
        originalContact.setGsmNo(orignalGsmNo);
        original.setContactInfo(originalContact);
        
        Customer copy = original.makeADeepCopy();
        copy.setFirst(modifiedFirst);
        copy.setLast(modifiedLast);
        copy.getContactInfo().setEmail(modifiedEmail);
        copy.getContactInfo().setGsmNo(modifiedGsmNo);
        
        assertNotEquals(modifiedFirst, original.getFirst());
        assertNotEquals(modifiedLast, original.getLast());
        assertNotEquals(modifiedEmail, original.getContactInfo().getEmail());
        assertNotEquals(modifiedGsmNo, original.getContactInfo().getGsmNo());
    }

}