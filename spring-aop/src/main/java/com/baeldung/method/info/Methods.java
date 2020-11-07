package com.baeldung.method.info;

public enum Methods {
  FOO_METHOD("I am the foo method"),
  BAR_METHOD("Im am the bar method");

  private String description;

  Methods(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
