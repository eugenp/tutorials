package com.baeldung.spring.boot.management.trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
import org.springframework.stereotype.Component;

@Component
public class TraceRequestFilter extends HttpTraceFilter {
  /**
   * Create a new {@link HttpTraceFilter} instance.
   *
   * @param repository the trace repository
   * @param tracer     used to trace exchanges
   */
  public TraceRequestFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
      super(repository, tracer);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
      return request.getServletPath().contains("actuator");
  }
}
