package org.baeldung.web.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.baeldung.web.main.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration   
@IntegrationTest("server.port:8080")
public class StudentServiceTest{

	private String getURL() {
        return "/StudentDirectory/student/get";
    }
	
	@Test
	public void whenResourcesAreRetrievedPaged_then200IsReceived(){
	   Response response = RestAssured.given().get(getURL()+ "?page=0&size=2").andReturn();
	   
	   assertTrue(response.getStatusCode() == 200 );
	}
	
	@Test
	public void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived(){
	   String url = getURL()+ "?page=" + RandomStringUtils.randomNumeric(5) + "&size=2";
	   Response response = RestAssured.given().get(url);
	 
	   assertTrue(response.getStatusCode() == 500 );
	}
	
	@Test
	public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources(){
	   Response response = RestAssured.given().get(getURL() + "?page=1&size=2" );
	   assertFalse(response.getBody().jsonPath().getList("content").isEmpty() );
	}
	
}
