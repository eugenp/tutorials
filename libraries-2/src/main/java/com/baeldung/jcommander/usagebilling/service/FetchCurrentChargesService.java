package com.baeldung.jcommander.usagebilling.service;

public interface FetchCurrentChargesService {

  static FetchCurrentChargesService getDefault() {
    return new DefaultFetchCurrentChargesService();
  }
}
