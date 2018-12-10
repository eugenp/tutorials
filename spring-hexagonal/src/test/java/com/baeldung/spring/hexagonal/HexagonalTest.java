package com.baeldung.spring.hexagonal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.nio.charset.Charset;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class HexagonalTest {

  @Autowired
  private MockMvc mockMvc;


  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset
      .forName("utf8"));


  @Test
  public void testCreateAthlete() throws Exception {
    Athlete wAthleteFTP = generateAthlete();
    createAthlete(wAthleteFTP);
  }

  private Athlete generateAthlete() {
    Athlete wAthleteFTP = new Athlete();
    wAthleteFTP.setMarathonTime(3.50);
    wAthleteFTP.setUsername(UUID.randomUUID().toString());
    return wAthleteFTP;
  }

  private void createAthlete(Athlete wAthleteFTP) throws Exception {
    mockMvc.perform(post("/api/athlete").contentType(contentType)
        .content(JsonUtil.convertObjectToJsonBytes(wAthleteFTP))).andReturn();
  }


}
