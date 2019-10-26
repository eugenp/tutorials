package com.baeldung.jasperreports;

import com.baeldung.jasperreports.config.JasperRerportsSimpleConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(JasperRerportsSimpleConfig.class);
        ctx.refresh();

        SimpleReportFiller simpleReportFiller = ctx.getBean(SimpleReportFiller.class);
        simpleReportFiller.setReportFileName("employeeEmailReport.jrxml");
        simpleReportFiller.compileReport();

        simpleReportFiller.setReportFileName("employeeReport.jrxml");
        simpleReportFiller.compileReport();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Employee Report Example");
        parameters.put("minSalary", 15000.0);
        parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

        simpleReportFiller.setParameters(parameters);
        simpleReportFiller.fillReport();

        SimpleReportExporter simpleExporter = ctx.getBean(SimpleReportExporter.class);
        simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

        simpleExporter.exportToPdf("employeeReport.pdf", "baeldung");
        simpleExporter.exportToXlsx("employeeReport.xlsx", "Employee Data");
        simpleExporter.exportToCsv("employeeReport.csv");
        simpleExporter.exportToHtml("employeeReport.html");

    }
}
