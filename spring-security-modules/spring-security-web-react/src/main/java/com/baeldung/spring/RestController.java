package com.baeldung.spring;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest")
public class RestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

  @GetMapping
  public ResponseEntity<Void> get(HttpServletRequest request) {
    CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
    LOGGER.info("{}={}", token.getHeaderName(), token.getToken());
    return ResponseEntity.ok().build();
  }
  
  @PostMapping
  public ResponseEntity<Void> post(HttpServletRequest request) {
    // Same impl as GET for testing purpose
    return this.get(request);
  }
}
