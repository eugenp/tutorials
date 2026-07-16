package com.baeldung.handlerinterceptor;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.lang.NonNull;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeliveryChargeInterceptor implements HandlerInterceptor {

    static final String USE_V2_ATTRIBUTE = "useV2";

    private final FeatureFlagService featureFlagService;
    private final SecureRandom random = new SecureRandom();
    private final Logger logger = LoggerFactory.getLogger(DeliveryChargeInterceptor.class);

    public DeliveryChargeInterceptor(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (handler instanceof HandlerMethod) {
            int rollout = featureFlagService.rolloutPercentage();
            boolean useV2 = rollout > 0 && random.nextInt(100) < rollout;
            request.setAttribute(USE_V2_ATTRIBUTE, useV2);
            logger.info("Delivery charge feature: rollout={}%, useV2={}", rollout, useV2);
        }
        return true;
    }
}
