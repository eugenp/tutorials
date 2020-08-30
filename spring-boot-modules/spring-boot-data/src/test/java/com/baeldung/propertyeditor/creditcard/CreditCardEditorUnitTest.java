package com.baeldung.propertyeditor.creditcard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardEditorUnitTest {
    
    private CreditCardEditor creditCardEditor;
    
    @Before
    public void setup() {
        creditCardEditor = new CreditCardEditor();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenInvalidCardNoWithLessDigits_thenThrowsException() {
        creditCardEditor.setAsText("123-123-123-123");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenInvalidCardNoWithNonDigits_thenThrowsException() {
        creditCardEditor.setAsText("1234-1234-xxxx-yyyy");
    }
    
    @Test
    public void whenCardNoWithNonDigits_parseCreditCard() {
        creditCardEditor.setAsText("1234-5678-9123-4560");
        
        CreditCard creditCard = (CreditCard) creditCardEditor.getValue();
        Assert.assertNotNull(creditCard);
        
        Assert.assertEquals(123456, creditCard.getBankIdNo().intValue());
        Assert.assertEquals(789123456, creditCard.getAccountNo().intValue());
        Assert.assertEquals(0, creditCard.getCheckCode().intValue());
    }

}
