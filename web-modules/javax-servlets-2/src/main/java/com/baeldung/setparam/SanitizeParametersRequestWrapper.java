package com.baeldung.setparam;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.text.StringEscapeUtils;

public class SanitizeParametersRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> sanitizedMap;

    public SanitizeParametersRequestWrapper(HttpServletRequest request) {
        super(request);
        sanitizedMap = Collections.unmodifiableMap(
            request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> Arrays.stream(entry.getValue())
                        .map(StringEscapeUtils::escapeHtml4)
                        .toArray(String[]::new)
                )));
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return sanitizedMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        return Optional.ofNullable(getParameterMap().get(name))
            .map(values -> Arrays.copyOf(values, values.length))
            .orElse(null);
    }

    @Override
    public String getParameter(String name) {
        return Optional.ofNullable(getParameterValues(name))
            .map(values -> values[0])
            .orElse(null);
    }

}