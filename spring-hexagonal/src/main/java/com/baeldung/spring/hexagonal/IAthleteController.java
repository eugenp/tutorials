package com.baeldung.spring.hexagonal;


public interface IAthleteController {

  public Athlete getAthlete(long id);

  public Athlete createOrUpdateAthlete(Athlete athlete) throws Exception;



}
