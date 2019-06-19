package com.baeldung.hexagonal.core.bo;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Slf4j
public class PostBo {

  private Long id;
  private String title;
  private String content;
}
