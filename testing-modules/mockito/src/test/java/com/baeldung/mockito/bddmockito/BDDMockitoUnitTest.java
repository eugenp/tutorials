package com.baeldung.mockito.bddmockito;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.*;


public class BDDMockitoUnitTest {
    
    PhoneBookService phoneBookService;
    PhoneBookRepository phoneBookRepository;
    
    String momContactName = "Mom";
    String momPhoneNumber = "01234";
    String xContactName = "x";
    String tooLongPhoneNumber = "01111111111111";
    
    @Before
    public void init() {
        phoneBookRepository = Mockito.mock(PhoneBookRepository.class);
        phoneBookService = new PhoneBookService(phoneBookRepository);
    }
    
    @Test
    public void givenValidContactName_whenSearchInPhoneBook_thenRetunPhoneNumber() {
        given(phoneBookRepository.contains(momContactName)).willReturn(true);
        given(phoneBookRepository.getPhoneNumberByContactName(momContactName))
          .will((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(momContactName)) {
                return momPhoneNumber;
            } else {
                return null;
            }
        });
        
        String phoneNumber = phoneBookService.search(momContactName);
        
        then(phoneBookRepository).should().contains(momContactName);
        then(phoneBookRepository).should().getPhoneNumberByContactName(momContactName);
        Assert.assertEquals(phoneNumber, momPhoneNumber);
    }
    
    @Test
    public void givenInvalidContactName_whenSearch_thenRetunNull() {
        given(phoneBookRepository.contains(xContactName)).willReturn(false);
        
        String phoneNumber = phoneBookService.search(xContactName);
        
        then(phoneBookRepository).should().contains(xContactName);
        then(phoneBookRepository).should(never()).getPhoneNumberByContactName(xContactName);
        Assert.assertEquals(phoneNumber, null);
    }
    
    @Test
    public void givenValidContactNameAndPhoneNumber_whenRegister_thenSucceed() {
        given(phoneBookRepository.contains(momContactName)).willReturn(false);
        
        phoneBookService.register(momContactName, momPhoneNumber);
        
        verify(phoneBookRepository).insert(momContactName, momPhoneNumber);
    }
    
    @Test
    public void givenEmptyPhoneNumber_whenRegister_thenFail() {
        given(phoneBookRepository.contains(momContactName)).willReturn(false);
        
        phoneBookService.register(xContactName, "");
        
        then(phoneBookRepository).should(never()).insert(momContactName, momPhoneNumber);
    }
    
    @Test
    public void givenLongPhoneNumber_whenRegister_thenFail() {
        given(phoneBookRepository.contains(xContactName)).willReturn(false);
        willThrow(new RuntimeException())
          .given(phoneBookRepository).insert(any(String.class), eq(tooLongPhoneNumber));
        
        try {
            phoneBookService.register(xContactName, tooLongPhoneNumber);
            fail("Should throw exception");
        } catch (RuntimeException ex) { }
        
        then(phoneBookRepository).should(never()).insert(momContactName, tooLongPhoneNumber);
    }
    
    @Test
    public void givenExistentContactName_whenRegister_thenFail() {
        given(phoneBookRepository.contains(momContactName))
          .willThrow(new RuntimeException("Name already exist"));
        
        try {
            phoneBookService.register(momContactName, momPhoneNumber);
            fail("Should throw exception");
        } catch(Exception ex) { }
        
        then(phoneBookRepository).should(never()).insert(momContactName, momPhoneNumber);
    }

}
