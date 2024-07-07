package com.baeldung.problemdetails.model;

public class InvalidOperandException extends RuntimeException {

  public InvalidOperandException(String s) {
    super(s);
  }
}
