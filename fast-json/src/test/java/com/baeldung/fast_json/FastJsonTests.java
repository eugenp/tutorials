package com.baeldung.fast_json;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;

public class FastJsonTests {
    private List<Person> listOfPersons = new ArrayList<Person>();

    @Before
    public void setUp() {
        listOfPersons.add(new Person(15, "John", "Doe", new Date()));
        listOfPersons.add(new Person(20, "Janette", "Doe", new Date()));
    }

    @Test
    public void convertObjectToJsonTest() {
        String personJsonFormat = JSON.toJSONString(listOfPersons);
        assertEquals(
                personJsonFormat,
                "[{\"FIRST NAME\":\"Doe\",\"LAST NAME\":\"John\",\"DATE OF BIRTH\":"
                        + "\"24/07/2016\"},{\"FIRST NAME\":\"Doe\",\"LAST NAME\":\"Janette\",\"DATE OF BIRTH\":"
                        + "\"24/07/2016\"}]");
    }

    @Test
    public void convertJsonFormatToObject() {
        String personJsonFormat = JSON.toJSONString(listOfPersons.get(0));
        Person newPerson = JSON.parseObject(personJsonFormat, Person.class);
        assertEquals(newPerson.getAge(), 0); // serialize is set to false for age attribute
        assertEquals(newPerson.getFirstName(), listOfPersons.get(0).getFirstName());
        assertEquals(newPerson.getLastName(), listOfPersons.get(0).getLastName()); 
    }
    
    @Test
    public void createJsonObjectTest() throws ParseException {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 2; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("FIRST NAME", "John" + i);
            jsonObject.put("LAST NAME", "Doe" + i);
            jsonObject.put("DATE OF BIRTH", "2016/12/12 12:12:12");
            jsonArray.add(jsonObject);
        }
        assertEquals(
                jsonArray.toString(),
                "[{\"LAST NAME\":\"Doe0\",\"DATE OF BIRTH\":"
                        + "\"2016/12/12 12:12:12\",\"FIRST NAME\":\"John0\"},{\"LAST NAME\":\"Doe1\","
                        + "\"DATE OF BIRTH\":\"2016/12/12 12:12:12\",\"FIRST NAME\":\"John1\"}]");
    }

    @Test
    public void convertObjectToJsonUsingContextFilterTest() {
        ContextValueFilter valueFilter = new ContextValueFilter() {
            public Object process(BeanContext context, Object object,
                    String name, Object value) {
                if (name.equals("DATE OF BIRTH")) {
                    return "NOT TO DISCLOSE";
                }
                if (value.equals("John") || value.equals("Doe")) {
                    return ((String) value).toUpperCase();
                } else {
                    return null;
                }
            }
        };
        JSON.toJSONString(listOfPersons, valueFilter);
    }

    @Test
    public void convertObjectToJsonUsingSerializeConfig() {
        NameFilter formatName = new NameFilter() {
            public String process(Object object, String name, Object value) {
                return name.toLowerCase().replace(" ", "_");
            }
        };
        SerializeConfig.getGlobalInstance().addFilter(Person.class, formatName);
        String jsonOutput = JSON.toJSONStringWithDateFormat(listOfPersons,
                "yyyy-MM-dd");
        assertEquals(
                jsonOutput,
                "[{\"first_name\":\"Doe\",\"last_name\":\"John\","
                        + "\"date_of_birth\":\"2016-07-24\"},{\"first_name\":\"Doe\",\"last_name\":"
                        + "\"Janette\",\"date_of_birth\":\"2016-07-24\"}]");
    }
}
