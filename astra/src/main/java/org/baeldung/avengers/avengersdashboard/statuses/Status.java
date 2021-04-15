package org.baeldung.avengers.avengersdashboard.statuses;

public record Status(String avenger, 
  String name, 
  String realName, 
  String status, 
  String location) {}