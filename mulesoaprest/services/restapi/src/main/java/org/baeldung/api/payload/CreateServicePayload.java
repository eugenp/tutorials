package org.baeldung.api.payload;

import java.util.HashMap;
import java.util.Map;

import org.baeldung.api.model.Person;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class CreateServicePayload extends AbstractMessageTransformer{

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		// TODO Auto-generated method stub
		//Creating a map with necessary paylaods
		
		
		Map<String, Person> Persons = new HashMap<String, Person>();
		Persons.put("baeldung", new Person("Baeldung",07,"Romania", "Bucharest"));
		Persons.put("shohag", new Person("Shohag",30,"USA","Dallas"));
		
		String authToken = message.getInboundProperty("authtoken");
		Map<String, String> pathParams = message.getInboundProperty("http.uri.params");
		String username = pathParams.get("username");
		Map<String,String> queryParams = message.getInboundProperty("http.query.params");
		String city = queryParams.get("city");
		
		System.out.println("Header params: "+authToken);
		
		
		
		System.out.println("pathparam"+username);
		System.out.println("queryParam"+city);
		
		Person person = Persons.get(username);
		System.out.println("Persons Name: "+ person.getName());
		 if(person !=null && person.getCity().equalsIgnoreCase(city)){
			 return person;
		 }
		 else 
			 return "Person not found";	
	}

}
