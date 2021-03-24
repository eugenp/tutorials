package com.baeldung.aspectj.classmethodadvice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Trace
@Component
public class MyTracedService {
  private static final Log LOG = LogFactory.getLog(MyTracedService.class);

  public void performSomeLogic() {
    LOG.info("Inside performSomeLogic...");
  }

  public void performSomeAdditionalLogic() {
    LOG.info("Inside performSomeAdditionalLogic...");
  }
}
