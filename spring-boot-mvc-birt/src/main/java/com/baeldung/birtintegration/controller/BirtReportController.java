package com.baeldung.birtintegration.controller;

import com.baeldung.birtintegration.dto.Report;
import com.baeldung.birtintegration.service.BirtReportService;
import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.EngineException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Report controller class
 *
 * @author koolshams
 *
 */
@Controller
public class BirtReportController {
    private static final Logger log = Logger.getLogger(BirtReportController.class);

    @Autowired
    private BirtReportService reportService;

    /**
     * generate list of reports
     */
    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/report")
    @ResponseBody
    public List<Report> listReports() {
        return reportService.getReports();
    }

    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/report/reload")
    @ResponseBody
    public ResponseEntity reloadReports(HttpServletResponse response) {
        try {
            log.info("Reloading reports");
            reportService.loadReports();
        } catch (EngineException e) {
            log.error("There was an error reloading the reports in memory: ", e);
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }


    /**
     * Generate full report
     */
    @RequestMapping(method = RequestMethod.GET, value = "/report/{name}")
    @ResponseBody
    public void generateFullReport(HttpServletResponse response, HttpServletRequest request,
                                   @PathVariable("name") String name) throws EngineException, IOException {
        log.info("Generating full report: " + name);
        reportService.generateMainReport(name, response, request);
    }

}
