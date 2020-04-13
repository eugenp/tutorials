package com.baeldung.jsonparam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RestController
@RequestMapping("/api")
public class JsonParamController {

    @GetMapping(value = "/something")
    public String testQueryParamApi(@RequestParam("params") String params) {
        // params={"type":"foo","color":"green"}
        ParamObjectDTO paramObjectDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            paramObjectDTO = objectMapper.readValue(params, ParamObjectDTO.class);
            System.out.println(paramObjectDTO);
            // use paramObjectDTO where you have {"type":"foo","color":"green"} JSON data as Object
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return params;
    }


    @PostMapping(value = "/something")
    public String testBodyParamApi(@RequestBody String params) {
        // params={"type":"foo","color":"green"}
        ParamObjectDTO paramObjectDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            paramObjectDTO = objectMapper.readValue(params, ParamObjectDTO.class);
            System.out.println(paramObjectDTO);
            // use paramObjectDTO where you have {"type":"foo","color":"green"} JSON data as Object
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return params;
    }

}
