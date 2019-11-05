package com.baeldug.json;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.json.Person;
import com.baeldung.json.PersonBuilder;
import com.baeldung.json.PersonWriter;

public class JsonUnitTest {
    private Person person;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private String personJson; 
    private String petshopJson; 
    
    @Test
    public void whenPersonIsConvertedToString_thenAValidJsonStringIsReturned() throws IOException {
        String generatedJsonString = new PersonWriter(person).write();
        
        assertEquals(
                "Generated String has the expected format and content", 
                personJson, 
                generatedJsonString);
    }
    
    @Test
    public void whenJsonStringIsConvertedToPerson_thenAValidObjectIsReturned(
            ) throws IOException, ParseException {        
        Person person = new PersonBuilder(personJson).build();
        
        assertEquals("First name has expected value", "Michael", person.getFirstName());
        assertEquals("Last name has expected value", "Scott", person.getLastName());
        assertEquals(
                "Birthdate has expected value", 
                dateFormat.parse("06/15/1978"), 
                person.getBirthdate());
        assertThat(
                "Email list has two items", 
                person.getEmails(), 
                hasItems("michael.scott@dd.com", "michael.scarn@gmail.com"));
    }

    @Test
    public void whenUsingObjectModelToQueryForSpecificProperty_thenExpectedValueIsReturned(
            ) throws IOException, ParseException {
        JsonReader reader = Json.createReader(new StringReader(petshopJson));

        JsonObject jsonObject = reader.readObject();
        
        assertEquals(
                "The query should return the 'name' property of the third pet from the list", 
                "Jake", 
                jsonObject.getJsonArray("pets").getJsonObject(2).getString("name"));
    }
    
    @Test
    public void whenUsingStreamingApiToQueryForSpecificProperty_thenExpectedValueIsReturned(
            ) throws IOException, ParseException {
        JsonParser jsonParser = Json.createParser(new StringReader(petshopJson));
        
        int count = 0;
        String result = null;
        
        while(jsonParser.hasNext()) {
            Event e = jsonParser.next();
            
            if (e == Event.KEY_NAME) {
                if(jsonParser.getString().equals("name")) {
                   jsonParser.next();
                   
                   if(++count == 3) {
                       result = jsonParser.getString();
                       break;
                   }
                }   
            }
        }
        
        assertEquals(
                "The query should return the 'name' property of the third pet from the list", 
                "Jake", 
                result);
    }    
    
    @Before
    public void init() throws ParseException {
        // Creates a Person object
        person = new Person();

        person.setFirstName("Michael");
        person.setLastName("Scott");
        person.setBirthdate(dateFormat.parse("06/15/1978"));
        person.setEmails(Arrays.asList("michael.scott@dd.com", "michael.scarn@gmail.com"));

        // Initializes the Person Json
        personJson = "\n" +
                "{\n" +
                "    \"firstName\":\"Michael\",\n" +
                "    \"lastName\":\"Scott\",\n" +
                "    \"birthdate\":\"06/15/1978\",\n" +
                "    \"emails\":[\n" +
                "        \"michael.scott@dd.com\",\n" +
                "        \"michael.scarn@gmail.com\"\n" +
                "    ]\n" +
                "}";
        
        // Initializes the Pet Shop Json
        petshopJson = "\n" +
        "{\n" +
        "    \"ownerId\":\"1\", \n" +
        "    \"pets\":[ \n" +
        "        {\"name\": \"Kitty\", \"type\": \"cat\"}, \n" +
        "        {\"name\": \"Rex\", \"type\": \"dog\"}, \n" +
        "        {\"name\": \"Jake\", \"type\": \"dog\"} \n" +
        "    ]\n" +
        "}";
    }
}
