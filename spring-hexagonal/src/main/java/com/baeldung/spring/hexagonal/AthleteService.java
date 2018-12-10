package com.baeldung.spring.hexagonal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AthleteService implements IAthleteService {

  IAthleteRepositoryService athleteRepositoryService;

  @Autowired
  public AthleteService(
      IAthleteRepositoryService athleteRepositoryService) {
    this.athleteRepositoryService = athleteRepositoryService;
  }

  @Override
  public Athlete getAthlete(long id) {
    return athleteRepositoryService.findOne(id);
  }

  @Override
  public Athlete updateMarathonTime(Athlete athlete) {
    if (athlete.getId() != null) {
      Athlete athlete1 = athleteRepositoryService.findOne(athlete.getId());
      athlete.setId(athlete1.getId());
    }
    return athleteRepositoryService.save(athlete);
  }
}
