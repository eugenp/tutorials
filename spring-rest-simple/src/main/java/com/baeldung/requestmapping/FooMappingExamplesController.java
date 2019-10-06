package com.baeldung.requestmapping;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ex")
public class FooMappingExamplesController {

    public FooMappingExamplesController() {
        super();
    }

    // API

    // mapping examples

    @RequestMapping(value = "/foos")
    @ResponseBody
    public String getFoosBySimplePath() {
        return "Simple Get some Foos";
    }

    // with @PathVariable

    @RequestMapping(value = "/foos/{id}")
    @ResponseBody
    public String getFoosBySimplePathWithPathVariable(@PathVariable final long id) {
        return "Get a specific Foo with id=" + id;
    }

    @RequestMapping(value = "/foos/{fooid}/bar/{barid}")
    @ResponseBody
    public String getFoosBySimplePathWithPathVariables(@PathVariable final long fooid, @PathVariable final long barid) {
        return "Get a specific Bar with id=" + barid + " from a Foo with id=" + fooid;
    }

    // other HTTP verbs

    @RequestMapping(value = "/foos", method = RequestMethod.POST)
    @ResponseBody
    public String postFoos() {
        return "Post some Foos";
    }

    // with headers

    @RequestMapping(value = "/foos", headers = "key=val")
    @ResponseBody
    public String getFoosWithHeader() {
        return "Get some Foos with Header";
    }

    @RequestMapping(value = "/foos", headers = { "key1=val1", "key2=val2" })
    @ResponseBody
    public String getFoosWithHeaders() {
        return "Get some Foos with Header";
    }

    // @RequestMapping(value = "/foos", method = RequestMethod.GET, headers = "Accept=application/json")
    // @ResponseBody
    // public String getFoosAsJsonFromBrowser() {
    // return "Get some Foos with Header Old";
    // }

    @RequestMapping(value = "/foos", produces = { "application/json", "application/xml" })
    @ResponseBody
    public String getFoosAsJsonFromREST() {
        return "Get some Foos with Header New";
    }

    // advanced - multiple mappings

    @RequestMapping(value = { "/advanced/bars", "/advanced/foos" })
    @ResponseBody
    public String getFoosOrBarsByPath() {
        return "Advanced - Get some Foos or Bars";
    }

    @RequestMapping(value = "*")
    @ResponseBody
    public String getFallback() {
        return "Fallback for GET Requests";
    }

    @RequestMapping(value = "*", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String allFallback() {
        return "Fallback for All Requests";
    }

    @RequestMapping(value = "/foos/multiple", method = { RequestMethod.PUT, RequestMethod.POST })
    @ResponseBody
    public String putAndPostFoos() {
        return "Advanced - PUT and POST within single method";
    }
 
    // --- Ambiguous Mapping
 
    @GetMapping(value = "foos/duplicate" )
    public String duplicate() {
        return "Duplicate";
    }
    
    // uncomment for exception of type java.lang.IllegalStateException: Ambiguous mapping

    // @GetMapping(value = "foos/duplicate" )
    // public String duplicateEx() {
    // return "Duplicate";
    // }
    
    @GetMapping(value = "foos/duplicate/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String duplicateXml() {
        return "Duplicate Xml";
    }
     
    @GetMapping(value = "foos/duplicate/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String duplicateJson() {
        return "Duplicate Json";
    }

}