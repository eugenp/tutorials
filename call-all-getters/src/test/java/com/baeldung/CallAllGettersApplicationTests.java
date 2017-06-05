package com.baeldung;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.reflection.model.Customer;
import com.baeldung.reflection.util.Utils;

public class CallAllGettersApplicationTests {

	@Test
	public void givenCustomer_whenAFieldIsNull_thenFieldNameInResult() throws Exception {
	    Customer customer = new Customer(1, "Himanshu", null, null);
	    
	    List<String> result = Utils.getNullPropertiesList(customer);
	    List<String> expectedFieldNames = Arrays.asList("emailId","phoneNumber");
	    
        Assert.assertTrue(result.size() == expectedFieldNames.size());
        Assert.assertTrue(result.containsAll(expectedFieldNames));
        
	}

}
