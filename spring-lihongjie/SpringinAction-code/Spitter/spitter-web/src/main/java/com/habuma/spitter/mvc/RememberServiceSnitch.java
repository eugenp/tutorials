package com.habuma.spitter.mvc;

import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

public class RememberServiceSnitch {
  private static final Logger LOGGER = Logger.getLogger(RememberServiceSnitch.class);
  
  public void tellMe() {
    LOGGER.error("************   KEY: " + rememberService.getKey());
  }
  
  public void setRememberService(AbstractRememberMeServices rememberService) {
    this.rememberService = rememberService;
  }

  public AbstractRememberMeServices getRememberService() {
    return rememberService;
  }

  private AbstractRememberMeServices rememberService;
}
