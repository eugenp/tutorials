package com.baeldung;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.reflection.model.Customer;
import com.baeldung.reflection.util.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCheckIfAPropertyIsNullApplicationTests {

	@Test
	public void givenCustomer_whenAFieldIsNull_thenFieldNameInResult() throws Exception {
	    Customer customer = new Customer(1, "Himanshu", null, null);
	    
	    List<String> result = Utils.getNullPropertiesList(customer);
	    List<String> expectedFieldNames = Arrays.asList("emailId","phoneNumber");
	    
        Assert.assertTrue(result.size() == expectedFieldNames.size());
        Assert.assertTrue(result.containsAll(expectedFieldNames));
        
	}

}
