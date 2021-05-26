package com.ivoronline.springboot_hexagonalarchitecture.userinterface;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface MyControllerInterface {

  @RequestMapping("/GetPerson")
  String getPerson(@RequestParam Integer id);

}
