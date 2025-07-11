package com.baeldung.parametrizedtypereference;

public record ApiResponse<T>(boolean success, String message, T data) {}
