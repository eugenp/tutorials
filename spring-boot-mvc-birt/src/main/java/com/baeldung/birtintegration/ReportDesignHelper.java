package com.baeldung.birtintegration;

import com.ibm.icu.util.ULocale;
import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.model.api.*;

import java.io.File;
import java.io.IOException;

public class ReportDesignHelper {
    private static final Logger log = Logger.getLogger(ReportDesignHelper.class);
    public static final String REPORTS_FOLDER = System.getProperty("user.dir") + "/reports";

    public static void main(String[] args) {
        try {
            new ReportDesignHelper().buildReport();
        } catch (IOException | BirtException e) {
            log.error("Exception while building report", e);
        }
    }

    private void buildReport() throws IOException, BirtException {
        final DesignConfig config = new DesignConfig();

        final IDesignEngine engine;
        try {
            Platform.startup(config);
            IDesignEngineFactory factory = (IDesignEngineFactory) Platform
              .createFactoryObject(IDesignEngineFactory.EXTENSION_DESIGN_ENGINE_FACTORY);
            engine = factory.createDesignEngine(config);

        } catch (Exception ex) {
            log.error("Exception during creation of DesignEngine", ex);
            throw ex;
        }

        SessionHandle session = engine.newSessionHandle(ULocale.ENGLISH);

        ReportDesignHandle design = session.createDesign();
        design.setTitle("Sample Report");

        // The element factory creates instances of the various BIRT elements.
        ElementFactory factory = design.getElementFactory();

        // Create a simple master page that describes how the report will
        // appear when printed.
        //
        // Note: The report will fail to load in the BIRT designer
        // unless you create a master page.

        DesignElementHandle element = factory.newSimpleMasterPage("Page Master"); //$NON-NLS-1$
        design.getMasterPages().add(element);

        // Create a grid and add it to the "body" slot of the report
        // design.

        GridHandle grid = factory.newGridItem(null, 2 /* cols */, 1 /* row */);
        design.getBody().add(grid);

        // Note: Set the table width to 100% to prevent the label
        // from appearing too narrow in the layout view.

        grid.setWidth("100%"); //$NON-NLS-1$

        // Get the first row.

        RowHandle row0 = (RowHandle) grid.getRows().get(0);

        // Create an image and add it to the first cell.

        ImageHandle image = factory.newImage(null);
        CellHandle cell = (CellHandle) row0.getCells().get(0);
        cell.getContent().add(image);
        image.setURL("\"https://www.baeldung.com/wp-content/themes/baeldung/favicon/favicon-96x96.png\"");
        // Create a label and add it to the second cell.

        LabelHandle label = factory.newLabel(null);
        cell = (CellHandle) row0.getCells().get(1);
        cell.getContent().add(label);
        label.setText("Hello, Baeldung world!");


        // Save the design and close it.
        File report = new File(REPORTS_FOLDER);
        report.mkdirs();

        design.saveAs(new File(report, "sample.rptdesign").getAbsolutePath());
        design.close();
        log.info("Report generated");
    }
}
