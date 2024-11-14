package com.baeldung.trace;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.Include;
import org.springframework.boot.actuate.web.exchanges.servlet.HttpExchangesFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class TraceRequestFilter extends HttpExchangesFilter {
  /**
   * Create a new {@link HttpExchangesFilter} instance.
   *
   * @param repository the trace repository
   */
  public TraceRequestFilter(HttpExchangeRepository repository) {
      super(repository, Include.defaultIncludes());
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
      return request.getServletPath().contains("actuator");
  }
}
