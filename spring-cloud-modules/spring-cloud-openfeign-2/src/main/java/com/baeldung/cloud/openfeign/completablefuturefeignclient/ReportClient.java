package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "reportClient", url = "http://localhost:8083")
public interface ReportClient {

    @RequestMapping(method = RequestMethod.POST, value = "/reports")
    void sendReport(@RequestBody String reportRequest);
}
