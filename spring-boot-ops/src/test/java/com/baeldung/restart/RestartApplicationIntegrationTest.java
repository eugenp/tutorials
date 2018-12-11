package com.baeldung.restart;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class RestartApplicationIntegrationTest {
    
    private TestRestTemplate template = new TestRestTemplate();

    @Test
    public void givenBootApp_whenRestart_thenOk() throws Exception {
        Application.main(new String[0]);
        
        ResponseEntity response = template.exchange("http://localhost:8080/restart", 
           HttpMethod.POST, null, Object.class);
        
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    public void givenBootApp_whenRestartUsingActuator_thenOk() throws Exception {
        Application.main(new String[] { "--server.port=8090" });
        
        ResponseEntity response = template.exchange("http://localhost:8090/restartApp", 
           HttpMethod.POST, null, Object.class);
        
        assertEquals(200, response.getStatusCode().value());
    }

}
