package com.baeldung.security.azuread;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("azuread")
class ApplicationLiveTest {
    
    @Autowired
    TestRestTemplate rest;
    
    @LocalServerPort
    int port;

    @Test
    void testWhenAccessRootPath_thenRedirectToAzureAD() {
                
        ResponseEntity<String> response = rest.getForEntity("http://localhost:" + port , String.class);               
        HttpStatus st = response.getStatusCode();
        assertThat(st)
           .isEqualTo(HttpStatus.FOUND);
        
        URI location1 = response.getHeaders().getLocation();
        assertThat(location1)
          .isNotNull();
        assertThat(location1.getPath())
          .isEqualTo("/oauth2/authorization/azure-dev");
        
        assertThat(location1.getPort())
          .isEqualTo(port);
        
        assertThat(location1.getHost())
          .isEqualTo("localhost");
        
        // Now let't follow this redirect
        response = rest.getForEntity(location1, String.class);
        assertThat(st)
        .isEqualTo(HttpStatus.FOUND);
        URI location2 = response.getHeaders().getLocation();
        assertThat(location2)
          .isNotNull();
        
        assertThat(location2.getHost())
          .describedAs("Should redirect to AzureAD")
          .isEqualTo("login.microsoftonline.com");
                 
    }

}
