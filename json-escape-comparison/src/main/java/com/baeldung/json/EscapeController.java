package com.baeldung.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class EscapeController {

    private final RestTemplate restTemplate;
    @Value("${service.url}")
    private String url;
    @Value("${escape.json}")
    private String escapeType;

    public EscapeController() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    @PostMapping
    public ResponseEntity handleInput(@RequestParam String input) {
        try {
            restTemplate.postForEntity(url, convertToJson(input), String.class);
            return ResponseEntity.accepted().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("check")
    public ResponseEntity handleCheck(@RequestBody String toCheck) {
        return ResponseEntity.ok(toCheck);
    }

    private String convertToJson(String input) throws JsonProcessingException {
        switch (escapeType) {
            case "jackson":
                final ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> value = new HashMap<>();
                value.put("timestamp", new Date().getTime());
                value.put("message", input);
                return objectMapper.writeValueAsString(value);
            case "json":
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("timestamp", new Date().getTime());
                jsonObject.put("message", input);
                return jsonObject.toString();
            case "gson":
                JsonObject gsonObject = new JsonObject();
                gsonObject.addProperty("timestamp", new Date().getTime());
                gsonObject.addProperty("message", input);
                return new Gson().toJson(gsonObject);
            default:
                return input;
        }
    }
}
