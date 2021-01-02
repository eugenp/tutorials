package org.spring.cloud.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @SentinelResource(value = "greeting", fallback = "getGreetingFallback")
    public String getGreeting() {
        return "Hello World!";
    }

    public String getGreetingFallback(Throwable e) {
        e.printStackTrace();
        return "Bye world!";
    }

}
