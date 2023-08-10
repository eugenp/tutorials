package com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.factories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.factories.ScrubResponseGatewayFilterFactory.Config;
import com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.factories.ScrubResponseGatewayFilterFactory.Scrubber;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

class ScrubResponseGatewayFilterFactoryUnitTest {
    
    private static final String JSON_WITH_FIELDS_TO_SCRUB = "{\r\n"
        + "  \"name\" : \"John Doe\",\r\n"
        + "        \"ssn\"  : \"123-45-9999\",\r\n"
        + "        \"account\" : \"9999888877770000\"\r\n"
        + "}";

    
    @Test
    void givenJsonWithFieldsToScrub_whenApply_thenScrubFields() throws Exception{
        
        JsonFactory jf = new JsonFactory(new ObjectMapper());
        JsonParser parser = jf.createParser(JSON_WITH_FIELDS_TO_SCRUB);
        JsonNode root = parser.readValueAsTree();
        
        Config config = new Config();
        config.setFields("ssn|account");
        config.setReplacement("*");
        Scrubber scrubber = new ScrubResponseGatewayFilterFactory.Scrubber(config);
        
        JsonNode scrubbed = Mono.from(scrubber.apply(null, root)).block();
        assertNotNull(scrubbed);
        assertEquals("*", scrubbed.get("ssn").asText());
    }

    @Test
    void givenJsonWithoutFieldsToScrub_whenApply_theBodUnchanged() throws Exception{
        
        JsonFactory jf = new JsonFactory(new ObjectMapper());
        JsonParser parser = jf.createParser(JSON_WITH_FIELDS_TO_SCRUB);
        JsonNode root = parser.readValueAsTree();
        
        Config config = new Config();
        config.setFields("xxxx");
        config.setReplacement("*");
        Scrubber scrubber = new ScrubResponseGatewayFilterFactory.Scrubber(config);
        
        JsonNode scrubbed = Mono.from(scrubber.apply(null, root)).block();
        assertNotNull(scrubbed);
        assertNotEquals("*", scrubbed.get("ssn").asText());
    }

}
