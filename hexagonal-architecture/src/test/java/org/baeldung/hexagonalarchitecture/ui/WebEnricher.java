package org.baeldung.hexagonalarchitecture.ui;

import org.baeldung.hexagonalarchitecture.base.User;

import java.util.List;
import java.util.stream.Collectors;

public class WebEnricher implements Enricher {

  @Override
  public List<User> enrich(List<User> users) {
    return users.stream()
      .map(this::enrichUser)
      .collect(Collectors.toList());
  }

  private User enrichUser(User user) {
    if (user.hasAvatar()) {
      return user;
    }

    user.setAvatar(
      "https://web-friendly-images.com/".concat(hashEmail(user.getEmailAddress())).concat("?size=50")
    );

    return user;
  }
}
