package com.baeldung.kubernetes.admission.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.kubernetes.admission.config.AdmissionControllerProperties;
import com.baeldung.kubernetes.admission.dto.AdmissionReviewResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties(AdmissionControllerProperties.class)
class AdmissionServiceUnitTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AdmissionService admissionService;

    @Test
    void whenAnnotationPresent_thenAddContainer() throws Exception {

        InputStream is = this.getClass()
            .getClassLoader()
            .getResourceAsStream("test1.json");
        JsonNode body = mapper.readTree(is);
        AdmissionReviewResponse response = admissionService.processAdmission((ObjectNode) body);
        assertNotNull(response);
        assertNotNull(response.getResponse());
        assertNotNull(response.getResponse());
        assertTrue(response.getResponse()
            .isAllowed());

        String jsonResponse = mapper.writeValueAsString(response);
        System.out.println(jsonResponse);

        // Decode Patch data
        String b64patch = response.getResponse()
            .getPatch();
        assertNotNull(b64patch);
        byte[] patch = Base64.getDecoder()
            .decode(b64patch);

        JsonNode root = mapper.reader()
            .readTree(new ByteArrayInputStream(patch));
        assertTrue(root instanceof ArrayNode);

        assertEquals(1, ((ArrayNode) root).size());

    }

}
