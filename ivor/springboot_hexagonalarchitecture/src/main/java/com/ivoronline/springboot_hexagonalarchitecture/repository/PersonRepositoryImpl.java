package com.ivoronline.springboot_hexagonalarchitecture.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonRepositoryImpl implements PersonRepositoryInterface {

  @Autowired PersonRepositoryStore personRepositoryStore;

  @Override
  public String getPerson(Integer id) {
    return personRepositoryStore.persons.get(id);
  }

}
