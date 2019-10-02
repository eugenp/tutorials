package com.baeldung.jcommander.usagebilling.service;

public interface SubmitUsageService {

  static SubmitUsageService getDefault() {
    return new DefaultSubmitUsageService();
  }
}
