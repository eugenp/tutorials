package com.baeldung.halbrowser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

  //access to global validator
  @Autowired
  @Lazy
  private Validator validator;

  @Override
  public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
    validatingListener.addValidator("beforeCreate", validator );
    validatingListener.addValidator("beforeSave", validator);
  }
}
