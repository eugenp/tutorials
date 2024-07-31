package com.baeldung.responsebody;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import jakarta.xml.bind.DatatypeConverter;

@SpringBootTest(classes = ResponseBodyApplication.class)
@AutoConfigureMockMvc
class ResponseBodyUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenExampleApiCallThenResponseHasMd5Header() throws Exception {
        String endpoint = "/api/example";
        String expectedResponse = "Hello, World!";
        String expectedMD5 = getMD5Hash(expectedResponse);

        MvcResult mvcResult = mockMvc.perform(get(endpoint).accept(MediaType.TEXT_PLAIN_VALUE))
            .andExpect(status().isOk())
            .andReturn();

        String md5Header = mvcResult.getResponse()
            .getHeader("Response-Body-MD5");
        assertThat(md5Header).isEqualTo(expectedMD5);
    }

    private String getMD5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        byte[] md5Hash = md5Digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(md5Hash);
    }
}