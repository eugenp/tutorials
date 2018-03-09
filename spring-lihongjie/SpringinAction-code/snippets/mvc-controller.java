//<start id="java_SpitterController_spittlesForSpitter_explicitPathVariable" />
@RequestMapping(value="/{username}/spittles", method=GET)
public String spittlesForSpitter(@PathVariable("username") String spitterName, 
        Map<String, Object> model) {
    Spitter spitter = spitterService.getSpitter(username);
    model.put("spitter", spitter);
    model.put("spittles", 
            spitterService.getSpittlesForSpitter(spitter));
    return "spitter/spittles";
} 
//<end id="java_SpitterController_spittlesForSpitter_explicitPathVariable" />