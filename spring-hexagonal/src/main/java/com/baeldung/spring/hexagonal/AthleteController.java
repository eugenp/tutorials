package com.baeldung.spring.hexagonal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AthleteController implements IAthleteController{

  IAthleteService athleteService;

  @Autowired
  public AthleteController(
      IAthleteService athleteService) {
    this.athleteService = athleteService;
  }

  @RequestMapping(value = "/athlete/{id}", method = RequestMethod.GET, produces = "application/json")
  public Athlete getAthlete(@PathVariable("id") long id) {
    Athlete athlete = athleteService.getAthlete(id);
    return athlete;
  }

  @RequestMapping(value = "/athlete", method = RequestMethod.POST, produces = "application/json")
  public Athlete createOrUpdateAthlete(@RequestBody Athlete athlete) throws Exception {
    return athleteService.updateMarathonTime(athlete);
  }


}
