package com.baeldung.requestheader.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class OperatorInterceptor implements HandlerInterceptor {

    private final OperatorHolder operatorHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String operator = request.getHeader("operator");
        operatorHolder.setOperator(operator);
        return true;
    }

    public OperatorInterceptor(OperatorHolder operatorHolder) {
        this.operatorHolder = operatorHolder;
    }
}
