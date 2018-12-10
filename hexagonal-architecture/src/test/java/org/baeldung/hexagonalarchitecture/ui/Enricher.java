package org.baeldung.hexagonalarchitecture.ui;

import org.baeldung.hexagonalarchitecture.base.User;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface Enricher {

  default String hashEmail(String email) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(email.getBytes());
      return DatatypeConverter.printHexBinary(md5.digest()).toUpperCase();
    } catch (NoSuchAlgorithmException ex) {
      return "";
    }
  }

  List<User> enrich(List<User> users);
}
