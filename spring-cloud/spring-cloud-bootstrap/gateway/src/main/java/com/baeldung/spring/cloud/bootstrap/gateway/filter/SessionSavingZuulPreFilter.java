package com.baeldung.spring.cloud.bootstrap.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionSavingZuulPreFilter extends ZuulFilter {

        private Logger log = LoggerFactory.getLogger(this.getClass());

        @Autowired
        private SessionRepository repository;

        @Override
        public boolean shouldFilter() {
                return true;
        }

        @Override
        public Object run() {
                RequestContext context = RequestContext.getCurrentContext();
                HttpSession httpSession = context.getRequest().getSession();
                Session session = repository.getSession(httpSession.getId());

                context.addZuulRequestHeader("Cookie", "SESSION=" + httpSession.getId());
                log.info("ZuulPreFilter session proxy: {}", session.getId());
                return null;
        }

        @Override
        public String filterType() {
                return "pre";
        }

        @Override
        public int filterOrder() {
                return 0;
        }
}
