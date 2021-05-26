package com.ivoronline.springboot_hexagonalarchitecture.businesslogic;

import com.ivoronline.springboot_hexagonalarchitecture.repository.PersonRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyBusinessLogic {

  @Autowired PersonRepositoryInterface personRepository;

  public String getPerson(Integer id) {
    return personRepository.getPerson(id);
  }

}
