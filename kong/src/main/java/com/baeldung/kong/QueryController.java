package com.baeldung.kong;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aiet
 */
@RestController
@RequestMapping("/stock")
public class QueryController {

    private static int REQUEST_COUNTER = 0;

    @GetMapping("/reqcount")
    public int getReqCount(){
        return REQUEST_COUNTER;
    }

    @GetMapping("/{code}")
    public String getStockPrice(@PathVariable String code){
        REQUEST_COUNTER++;
        if("BTC".equalsIgnoreCase(code))
            return "10000";
        else return "N/A";
    }



}
