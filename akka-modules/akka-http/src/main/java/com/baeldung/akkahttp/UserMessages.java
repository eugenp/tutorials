package com.baeldung.akkahttp;

import java.io.Serializable;

public interface UserMessages {

  class ActionPerformed implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String description;

    public ActionPerformed(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  class CreateUserMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private final User user;

    public CreateUserMessage(User user) {
      this.user = user;
    }

    public User getUser() {
      return user;
    }
  }

  class GetUserMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Long userId;

    public GetUserMessage(Long userId) {
      this.userId = userId;
    }

    public Long getUserId() {
      return userId;
    }
  }

}
