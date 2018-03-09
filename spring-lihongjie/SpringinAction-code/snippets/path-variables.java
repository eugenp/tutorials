//<start id="java_pathvariablename" />
@RequestMapping(value="/{username}", method=GET)
public String spittlesForSpitter(
        @PathVariable("username") String spitterName, 
        Map<String, Object> model) {
   // ...
}
//<end id="java_pathvariablename" />
