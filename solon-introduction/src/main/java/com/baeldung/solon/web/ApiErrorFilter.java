package com.baeldung.solon.web;

import java.util.Map;
import java.util.NoSuchElementException;

import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;

@Component
public class ApiErrorFilter implements Filter {

    @Override
    public void doFilter(Context context, FilterChain chain) throws Throwable {
        try {
            chain.doFilter(context);
        } catch (IllegalArgumentException exception) {
            context.status(400);
            context.render(Map.of("message", exception.getMessage()));
        } catch (NoSuchElementException exception) {
            context.status(404);
            context.render(Map.of("message", exception.getMessage()));
        }
    }
}
