package com.baeldung.templates;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;

import java.util.Map;
import java.util.function.Supplier;

public class StringView implements View {
    private final Supplier<String> output;

    public StringView(Supplier<String> output) {
        this.output = output;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.getWriter().write(output.get());
    }
}
