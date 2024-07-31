package com.baeldung.blade.sample.interceptors;

import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;

public class BaeldungMiddleware implements WebHook {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BaeldungMiddleware.class);

    @Override
    public boolean before(RouteContext context) {
        log.info("[BaeldungMiddleware] called before Route method and other WebHooks");
        return true;
    }
}