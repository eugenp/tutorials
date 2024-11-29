package com.baeldung.quarkus.langchain4j.camel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
class CamelStructurizeAPIResourceLiveTest {
    Logger logger = LoggerFactory.getLogger(CamelStructurizeAPIResourceLiveTest.class);

    String questionnaireResponses = """
      Operator: Could you provide your name?
      Patient: Hello, My name is Sara Connor.
                 
      Operator: Could you please give me your birth date?
      Patient: 1986, July the 10th.
      
      Operator: What is the main reason for your visit today?
      Patient: Hello, I'm calling because I need to declare an accident on my main vehicle.
          
      Operator: What is the main reason for your visit today?
      Patient: Hello, I'm calling because I need to declare an accident on my main vehicle.
          
      Operator: Do you have any known allergies, especially to medications?
      Patient: Yes, I’m allergic to penicillin. I also get mild reactions to certain over-the-counter antihistamines.
       
      Operator: Are you currently taking any medications, vitamins, or supplements?
      Patient: Yes, I’m taking a daily blood pressure medication (Lisinopril, 10 mg) and a multivitamin. I also take Vitamin D occasionally.
      """;

    @Test
    void givenHttpRouteWithStructurizingService_whenSendUnstructuredDialog_thenExpectedStructuredDataIsPresent() throws JsonProcessingException {
        ObjectWriter writer = new ObjectMapper().writer();
        String requestBody = writer.writeValueAsString(Map.of("content", questionnaireResponses));

        Response response = RestAssured.given()
          .when()
          .contentType(ContentType.JSON)
          .body(requestBody)
          .post("/structurize");

        logger.info(response.prettyPrint());

        response
          .then()
          .statusCode(200)
          .body("patientName", containsString("Sara Connor"))
          .body("patientBirthday", containsString("1986-07-10"))
          .body("visitReason", containsString("Declaring an accident on main vehicle"));
   }
}
