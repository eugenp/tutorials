package org.baeldung.hexagonalarchitecture.base;

public class User {
  private String emailAddress;
  private String name;
  private String avatar;

  public User(String emailAddress, String name) {
    this.emailAddress = emailAddress;
    this.name = name;
    this.avatar = null;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getName() {
    return name;
  }

  public String getAvatar() {
    return avatar;
  }

  public boolean hasAvatar() {
    return avatar != null && !avatar.isEmpty();
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public User withAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }
}