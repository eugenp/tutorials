package com.baeldung.micronaut.httpfilters.filters;

import com.baeldung.micronaut.httpfilters.service.LogService;

import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.http.filter.FilterPatternStyle;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ServerFilter
public class PatternFilter {

    private final LogService logService;

    public PatternFilter(LogService logService) {
        this.logService = logService;
    }

    @RequestFilter(patterns = {"/pattern", "/pattern2", "/pattern3"})
    @ExecuteOn(TaskExecutors.BLOCKING)
    public void filterRequest1(HttpRequest<?> request) {
        logService.logPatternFilter(request, "array pattern");
    } 

    @RequestFilter(patternStyle = FilterPatternStyle.REGEX, patterns = {"/pattern[3-5]?"})
    @ExecuteOn(TaskExecutors.BLOCKING)
    public void filterRequest2(HttpRequest<?> request) {
        logService.logPatternFilter(request, "regex pattern");
    } 

    @RequestFilter(value="/pattern-m", methods={HttpMethod.HEAD})
    @ExecuteOn(TaskExecutors.BLOCKING)
    public void filterRequest3(HttpRequest<?> request) {
        logService.logPatternFilter(request, "method pattern");
    } 
}