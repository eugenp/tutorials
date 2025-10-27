package com.baeldung.mockingobjectproperties;

import javax.servlet.http.HttpServletRequest;

public class MockingObjectProperties {

    public Object getSearchAttribute(HttpServletRequest request) {
        return request.getAttribute("search");
    }

    public void printSearchAttribute(HttpServletRequest request) {
        Object value = getSearchAttribute(request);
        System.out.println("Search attribute: " + value);
    }
}
