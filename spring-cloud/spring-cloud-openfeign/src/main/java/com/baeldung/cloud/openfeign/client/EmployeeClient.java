package com.baeldung.cloud.openfeign.client;

import com.baeldung.cloud.openfeign.model.Employee;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface EmployeeClient {
    @RequestLine("GET /empployee/{id}?active={isActive}")
    @Headers("Content-Type: application/json")
    Employee getEmployee(@Param long id, @Param boolean isActive);
}