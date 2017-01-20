package com.baeldung.birt.controller;

import com.baeldung.birt.service.ReportService;
import com.baeldung.birt.utils.ReportType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/reports")
class ReportController {

    private Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    @Autowired
    ReportController(ReportService reportService) {

        this.reportService = reportService;

    }

    @RequestMapping(value = "/{reportName}/{reportTypeName}", method = RequestMethod.GET)
    @ResponseBody
    byte[] renderReport(@PathVariable("reportName") String reportName, @PathVariable("reportTypeName") String reportTypeName, @RequestParam Map<String, String> reportParameters, HttpServletResponse response) {

        if (null == reportParameters) reportParameters = new HashMap();

        ReportType reportType = null;

        try {
            if (null == reportTypeName) reportType = ReportType.HTML;
            else reportType = ReportType.valueOf(reportTypeName.toUpperCase());
        } catch (IllegalArgumentException ex) {

            logger.error("Error while converting reportType param to enum.");
            //set to default type => HTML
            reportType = ReportType.HTML;

        }

        String contentType = null;

        switch (reportType) {

        case PDF:
            contentType = "application/pdf";
            break;

        case XLS:
            contentType = "application/ms-excel";
            break;

        case HTML:
            contentType = "text/html";
            break;

        }

        response.setContentType(contentType);

        if (reportType != ReportType.HTML) response.setHeader("Content-Disposition", "attachment;filename=" + reportName + "." + reportType
          .name()
          .toLowerCase());

        return reportService
          .generateReport(reportName, reportType, reportParameters)
          .toByteArray();
    }
}
