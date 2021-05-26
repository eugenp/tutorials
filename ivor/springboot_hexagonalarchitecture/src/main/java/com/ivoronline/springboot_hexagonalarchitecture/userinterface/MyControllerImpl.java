package com.ivoronline.springboot_hexagonalarchitecture.userinterface;

import com.ivoronline.springboot_hexagonalarchitecture.businesslogic.MyBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyControllerImpl {

  @Autowired
  MyBusinessLogic myService;

  @RequestMapping("/GetPerson")
  public String getPerson(@RequestParam Integer id) {
    return myService.getPerson(id);
  }

}
