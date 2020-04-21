package com.baeldung.jsonparam;

import com.baeldung.controllers.DeferredResultController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api")
public class JsonParamController {
    private final static Logger LOG = LoggerFactory.getLogger(DeferredResultController.class);

    @GetMapping(value = "/tickets")
    public String testQueryParamApi(@RequestParam("params") String params) {
        // params={"type":"foo","color":"green"}
        ParamObjectDTO paramObjectDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            paramObjectDTO = objectMapper.readValue(params, ParamObjectDTO.class);
            System.out.println(paramObjectDTO);
            // use paramObjectDTO where you have {"type":"foo","color":"green"} JSON data as Object
        } catch (JsonProcessingException e) {
            LOG.info("Json Processing Exception");
        }
        return params;
    }

    @GetMapping(value = "/tickets2")
    public String testGetBodyParamApi(@RequestBody String params) {
        // params={"type":"foo","color":"green"}
        ParamObjectDTO paramObjectDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            paramObjectDTO = objectMapper.readValue(params, ParamObjectDTO.class);
            System.out.println(paramObjectDTO);
            // use paramObjectDTO where you have {"type":"foo","color":"green"} JSON data as Object
        } catch (JsonProcessingException e) {
            LOG.info("Json Processing Exception");
        }
        return params;
    }

    @PostMapping(value = "/tickets")
    public String testBodyParamApi(@RequestBody String params) {
        // params={"type":"foo","color":"green"}
        ParamObjectDTO paramObjectDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            paramObjectDTO = objectMapper.readValue(params, ParamObjectDTO.class);
            System.out.println(paramObjectDTO);
            // use paramObjectDTO where you have {"type":"foo","color":"green"} JSON data as Object
        } catch (JsonProcessingException e) {
            LOG.info("Json Processing Exception");
        }
        return params;
    }

}
