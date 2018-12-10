package com.baeldung.spring.hexagonal;

public interface IAthlete {

  public String getUsername();

  public void setUsername(String username);

  public double getMarathonTime();

  public void setMarathonTime(double marathonTime);

}
