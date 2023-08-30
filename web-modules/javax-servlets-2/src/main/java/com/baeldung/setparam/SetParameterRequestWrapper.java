package com.baeldung.setparam;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SetParameterRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> paramMap;

    public SetParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        paramMap = new HashMap<>(request.getParameterMap());
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(paramMap);
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

    public void setParameter(String name, String value) {
        paramMap.put(name, new String[] {value});
    }

}