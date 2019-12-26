package com.baeldung.blade.sample.interceptors;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;

@Bean
public class BaeldungHook implements WebHook {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BaeldungHook.class);

    @Override
    public boolean before(RouteContext ctx) {
        log.info("[BaeldungHook] called before Route method");
        return true;
    }
}