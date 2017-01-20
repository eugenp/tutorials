package com.baeldung.birt.service;

import com.baeldung.birt.utils.ReportType;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.eclipse.birt.core.data.DataTypeUtil;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.eclipse.birt.report.model.api.*;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.core.internal.registry.RegistryProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    private Logger logger = LoggerFactory.getLogger(ReportService.class);

    public static final String BIRT_REPORT_DESIGN_DIR = "reports/design/";

    public static final String DATE_PARAM_FORMAT = "yyyy/MM/dd";
    public static final String DATE_TIME_PARAM_FORMAT = "yyyy/MM/dd HH:mm";
    public static final String TIME_PARAM_FORMAT = "HH:mm";

    private IReportEngine birtReportEngine;

    private final ApplicationContext applicationContext;

    private final String dbUser;

    private final String dbPassword;

    private final String dbUrl;

    @Autowired
    public ReportService(DataSource dataSource, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.dbUser = dataSource
          .getDbProperties()
          .getProperty("user");
        this.dbPassword = dataSource
          .getDbProperties()
          .getProperty("password");
        this.dbUrl = dataSource
          .getPoolProperties()
          .getUrl();
    }

    @PostConstruct
    public void startUp() {

        try {
            EngineConfig engineConfig = new EngineConfig();
            RegistryProviderFactory.releaseDefault();
            Platform.startup(engineConfig);
            IReportEngineFactory reportEngineFactory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            birtReportEngine = reportEngineFactory.createReportEngine(engineConfig);

        } catch (BirtException ex) {

            logger.error(ex.getMessage(), ex);

        }
    }

    public ByteArrayOutputStream generateReport(String reportName, ReportType reportType, Map<String, String> reportParams) {

        ByteArrayOutputStream byteArrayOutputStream;

        InputStream rptDesignFile;

        Resource resource = applicationContext.getResource("classpath:" + BIRT_REPORT_DESIGN_DIR + reportName + ".rptdesign");

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Report design file" + reportName + ".rptdesign either did not exist or was not readable.");
        }

        try {
            rptDesignFile = resource.getInputStream();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Unable to open report design file.");
        }

        byteArrayOutputStream = new ByteArrayOutputStream();

        IRunAndRenderTask runAndRenderTask = null;

        try {

            IReportRunnable reportDesign = birtReportEngine.openReportDesign(rptDesignFile);

            ReportDesignHandle designHandle = (ReportDesignHandle) reportDesign.getDesignHandle();

            SlotHandle slotHandle = designHandle.getDataSources();

            SlotIterator iter = (SlotIterator) slotHandle.iterator();
            while (iter.hasNext()) {
                DesignElementHandle dsHandle = (DesignElementHandle) iter.next();
                if (dsHandle instanceof OdaDataSourceHandle) {

                    try {

                        dsHandle.setProperty("odaDriverClass", "com.mysql.jdbc.Driver");
                        dsHandle.setProperty("odaURL", dbUrl);
                        dsHandle.setProperty("odaUser", dbUser);
                        dsHandle.setProperty("odaPassword", dbPassword);

                    } catch (SemanticException ex) {
                        logger.debug("Error while setting data source property", ex);
                    }

                } else {
                    logger.debug("ignoring non OdaDataSourceHandle ..");
                }
            }

            runAndRenderTask = birtReportEngine.createRunAndRenderTask(reportDesign);

            runAndRenderTask.setParameterValues(getRuntimeParameters(birtReportEngine, reportDesign, reportParams));

            runAndRenderTask.validateParameters();

            RenderOption renderOptions = null;

            switch (reportType) {

            case PDF:
                renderOptions = new PDFRenderOption();
                renderOptions.setOption(IPDFRenderOption.REPAGINATE_FOR_PDF, new Boolean(true));
                renderOptions.setOutputFormat(IRenderOption.OUTPUT_FORMAT_PDF);
                runAndRenderTask.setRenderOption(renderOptions);
                break;

            case HTML:

                renderOptions = new HTMLRenderOption();
                renderOptions.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);

                break;

            case XLS:

                renderOptions = new EXCELRenderOption();
                renderOptions.setOutputFormat("xls_spudsoft");

                //SpudSoft Emitter
                renderOptions.setEmitterID("uk.co.spudsoft.birt.emitters.excel.XlsEmitter");
                renderOptions.setOption("ExcelEmitter.SingleSheetWithPageBreaks", true);

                break;
            }

            renderOptions.setOutputStream(byteArrayOutputStream);

            runAndRenderTask.setRenderOption(renderOptions);
            runAndRenderTask.run();

        } catch (EngineException e) {
            logger.error(e.getMessage(), e);

            throw new RuntimeException(e);
        } finally {
            runAndRenderTask.close();
        }

        return byteArrayOutputStream;
    }

    private Map getRuntimeParameters(IReportEngine reportEngine, IReportRunnable reportDesign, Map runtimeProperties) {

        IGetParameterDefinitionTask reportParameters = reportEngine.createGetParameterDefinitionTask(reportDesign);

        Collection paramDefs = reportParameters.getParameterDefns(false);

        Map paramMap = new HashMap();

        for (Object param : paramDefs) {

            Object paramVal = null;
            IParameterDefnBase iParameterDefnBase = ((IParameterDefnBase) param);
            String paramKey = iParameterDefnBase.getName();

            if (runtimeProperties.containsKey(paramKey)) {
                paramVal = toParamValue(iParameterDefnBase.getParameterType(), (String) runtimeProperties.get(paramKey));
            }

            paramMap.put(paramKey, paramVal);

        }

        return paramMap;
    }

    private Object toParamValue(int dataType, String data) {

        try {

            SimpleDateFormat simpleDateFormat;

            switch (dataType) {

            case IScalarParameterDefn.TYPE_BOOLEAN:
                return DataTypeUtil.toBoolean(data);

            case IScalarParameterDefn.TYPE_DATE:
                simpleDateFormat = new SimpleDateFormat(DATE_PARAM_FORMAT);
                if (null != data && !data
                  .trim()
                  .isEmpty()) return DataTypeUtil.toSqlDate(simpleDateFormat.parse(data));
                else return "";

            case IScalarParameterDefn.TYPE_TIME:
                simpleDateFormat = new SimpleDateFormat(TIME_PARAM_FORMAT);
                return DataTypeUtil.toSqlTime(simpleDateFormat.parse(data));

            case IScalarParameterDefn.TYPE_DATE_TIME:
                simpleDateFormat = new SimpleDateFormat(DATE_TIME_PARAM_FORMAT);
                return DataTypeUtil.toDate(simpleDateFormat.parse(data));

            case IScalarParameterDefn.TYPE_DECIMAL:
                return DataTypeUtil.toBigDecimal(data);

            case IScalarParameterDefn.TYPE_FLOAT:
                return DataTypeUtil.toDouble(data);

            case IScalarParameterDefn.TYPE_STRING:
                return DataTypeUtil.toString(data);

            case IScalarParameterDefn.TYPE_INTEGER:
                return DataTypeUtil.toInteger(data);
            }

        } catch (Exception ex) {

            String errorMessage = "Invalid Report Parameter Value: ${data}";

            logger.error(errorMessage, ex);

            throw new RuntimeException(errorMessage);
        }

        return null;
    }

}
