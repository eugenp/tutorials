package com.baeldung.spring.hexagonal;

public interface IAthleteService {

  Athlete getAthlete(long id);

  Athlete updateMarathonTime(Athlete athlete);
}
