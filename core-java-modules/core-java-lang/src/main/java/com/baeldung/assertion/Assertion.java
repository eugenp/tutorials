package com.baeldung.assertion;

/**
 * Simple demonstration of using Java assert keyword.
 */
public class Assertion {

  public static void main(String[] args) {
    Assertion assertion = new Assertion();
    assertion.setup();
  }

  public void setup() {
    Object conn = getConnection();
    assert conn != null : "Connection is null";

    // continue with other setup ...
  }

  // Simulate failure to get a connection; using Object
  // to avoid dependencies on JDBC or some other heavy
  // 3rd party library
  public Object getConnection() {
    return null;
  }
}
