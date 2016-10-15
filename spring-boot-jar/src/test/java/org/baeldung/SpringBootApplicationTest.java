package org.baeldung;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.domain.GenericEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootApplicationTest {
   
    @Autowired
    private TestRestTemplate restTemplate;
    private JacksonTester<List<GenericEntity>> jsonList;
    
    private List<GenericEntity> entityList = new ArrayList<GenericEntity>();
    {
        // Create some example entities to store
        entityList.add(new GenericEntity(1l, "entity_1"));
        entityList.add(new GenericEntity(2l, "entity_2"));
        entityList.add(new GenericEntity(3l, "entity_3"));
        entityList.add(new GenericEntity(4l, "entity_4"));
    }
    
    @Before
    public void setup() {
        ObjectMapper objectMappper = new ObjectMapper(); 
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMappper);
    }

    @Test
    public void givenRequestHasBeenMade_whenMeetsAllOfGivenConditions_thenCorrect() throws Exception {
        
        for (GenericEntity genericEntity : entityList) {
            ResponseEntity<GenericEntity> postResponse = this.restTemplate.postForEntity("/rest/entity", genericEntity, GenericEntity.class);
            GenericEntity responseBody = postResponse.getBody();
            assertThat(responseBody).isEqualToComparingFieldByField(genericEntity);
        }
        
        ResponseEntity<String> getResponse = this.restTemplate.getForEntity("/rest/entity/all", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isEqualTo(jsonList.write(entityList).getJson());
    }
}
