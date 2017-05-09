package com.baeldung.teng.invoicing.web.view.stringtemplate;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.InternalResourceView;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STRawGroupDir;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class StringTemplateView extends InternalResourceView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        String templateName = getBeanName();
        int extPos = templateName.lastIndexOf('.');
        if (extPos > 0) {
            templateName = templateName.substring(0, extPos);
        }

        Resource templateResource = getApplicationContext().getResource(getUrl());

        //! TODO
        //! The ST group gets created (hence other potential operations repeated needlessly) with each view
        //! rendering => for a production-ready solution, further customization and/or design is necessary!

        // Use the $ sign (instead of the default < and > signs) to delimit ST expressions
        // since we are generating HTML (which already makes use of <, etc.). Also, load
        // the file as a template and not as a group to not clutter its overall syntax.
        ST template =
            new STRawGroupDir(templateResource.getFile().getParent(), "UTF-8", '$', '$').getInstanceOf(templateName);
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            template.add(entry.getKey(), entry.getValue());
        }

        PrintWriter writer = response.getWriter();
        writer.print(template.render());
        writer.flush();
        writer.close();
    }
}
