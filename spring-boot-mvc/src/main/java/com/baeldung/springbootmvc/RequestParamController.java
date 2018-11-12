package com.baeldung.springbootmvc;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class RequestParamController {

    @GetMapping("/api/foos")
    @ResponseBody
    public String getFoos(@RequestParam int limit){
        return "Limit: " + limit;
    }
    
    @GetMapping("/api/bars")
    @ResponseBody
    public String getBars(@RequestParam(name = "query") String searchQuery){ 
        return "Query: " + searchQuery;
    }
    
    @GetMapping("/api/users")
    @ResponseBody
    public String getUsers(@RequestParam(required = false) String query){ 
        return "Query: " + query;
    }
    
    @GetMapping("/api/products")
    @ResponseBody
    public String getProducts(@RequestParam(defaultValue = "20") int limit){
        return "Limit: " + limit;
    }
    
    @PostMapping("/api/foos")
    @ResponseBody
    public String updateFoos(@RequestParam Map<String,String> allParams){
        return "Parameters are " + allParams.entrySet();
    }
    
    @PostMapping("/api/posts")
    @ResponseBody
    public String createPost(@RequestParam String content, @RequestParam MultipartFile file){
        return "File size in bytes: " + file.getSize();
    }
    
    @GetMapping("/api/posts")
    @ResponseBody
    public String getPosts(@RequestParam List<String> id){
        return "ID are " + id;
    }
    
    @GetMapping("/foos/{id}")
    @ResponseBody
    public String getFooById(@PathVariable String id){
        return "ID: " + id;
    }
    
    @GetMapping("/foos")
    @ResponseBody
    public String getFooByIdUsingQueryParam(@RequestParam String id){
        return "ID: " + id;
    }
    
    @GetMapping({"/myfoos/optional", "/myfoos/optional/{id}"})
    @ResponseBody
    public String getFooByOptionalId(@PathVariable(required = false) String id){
        return "ID: " + id;
    }
    
    @GetMapping("/myfoos/optionalParam")
    @ResponseBody
    public String getFooByOptionalIdUsingQueryParam(@RequestParam(required = false) String id){
        return "ID: " + id;
    }
    
    
    
}
