package com.baeldung.git;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommitIdCommitIdApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnCommitDto() throws Exception {
        String body = this.restTemplate.getForObject("/commitId", String.class);
        System.out.println(body);
    }
}