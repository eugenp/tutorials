package com.baeldung.birtintegration.service;

import com.baeldung.birtintegration.ReportDesignHelper;
import com.baeldung.birtintegration.dto.Report;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Service
public class BirtReportService implements ApplicationContextAware, DisposableBean {

    @Autowired
    private ServletContext servletContext;

    private IReportEngine birtEngine;
    private ApplicationContext context;

    private Map<String, IReportRunnable> reports = new HashMap<>();

    private static final String IMAGE_FOLDER = "/images";

    @SuppressWarnings("unchecked")
    @PostConstruct
    protected void initialize() throws BirtException {
        EngineConfig config = new EngineConfig();
        config.getAppContext().put("spring", this.context);
        Platform.startup(config);
        IReportEngineFactory factory = (IReportEngineFactory) Platform
          .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        birtEngine = factory.createReportEngine(config);
        loadReports();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Load report files to memory
     *
     */
    public void loadReports() throws EngineException {
        File folder = new File(ReportDesignHelper.REPORTS_FOLDER);
        for (String file : Objects.requireNonNull(folder.list())) {
            if (!file.endsWith(".rptdesign")) {
                continue;
            }

            reports.put(file.replace(".rptdesign", ""),
              birtEngine.openReportDesign(folder.getAbsolutePath() + File.separator + file));

        }
    }

    public List<Report> getReports() {
        List<Report> response = new ArrayList<>();
        for (Map.Entry<String, IReportRunnable> entry : reports.entrySet()) {
            IReportRunnable report = reports.get(entry.getKey());
            IGetParameterDefinitionTask task = birtEngine.createGetParameterDefinitionTask(report);
            Report reportItem = new Report(report.getDesignHandle().getProperty("title").toString(), entry.getKey());
            for (Object h : task.getParameterDefns(false)) {
                IParameterDefn def = (IParameterDefn) h;
                reportItem.getParameters()
                  .add(new Report.Parameter(def.getPromptText(), def.getName(), getParameterType(def)));
            }
            response.add(reportItem);
        }
        return response;
    }

    private Report.ParameterType getParameterType(IParameterDefn param) {
        if (IParameterDefn.TYPE_INTEGER == param.getDataType()) {
            return Report.ParameterType.INT;
        }
        return Report.ParameterType.STRING;
    }

    public void generateMainReport(String reportName, HttpServletResponse response, HttpServletRequest request) {
        generateHTMLReport(reports.get(reportName), response, request);
    }

    /**
     * Generate a report as html
     */
    @SuppressWarnings("unchecked")
    private void generateHTMLReport(IReportRunnable report, HttpServletResponse response, HttpServletRequest request) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType("html"));
        IRenderOption options = new RenderOption();
        HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
        htmlOptions.setOutputFormat("html");
        runAndRenderTask.setRenderOption(htmlOptions);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST, request);

        try {
            htmlOptions.setOutputStream(response.getOutputStream());
            runAndRenderTask.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    @Override public void destroy() {
        birtEngine.destroy();
        Platform.shutdown();
    }
}
