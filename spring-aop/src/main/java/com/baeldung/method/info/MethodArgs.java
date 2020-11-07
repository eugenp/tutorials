package com.baeldung.method.info;

public class MethodArgs {
  private String fooArg;
  private String barArg;

  public String getFooArg() {
    return fooArg;
  }

  public void setFooArg(String fooArg) {
    this.fooArg = fooArg;
  }

  public String getBarArg() {
    return barArg;
  }

  public void setBarArg(String barArg) {
    this.barArg = barArg;
  }

  @Override
  public String toString() {
    return "MethodArgs{" +
        "fooArg='" + fooArg + '\'' +
        ", barArg='" + barArg + '\'' +
        '}';
  }
}
