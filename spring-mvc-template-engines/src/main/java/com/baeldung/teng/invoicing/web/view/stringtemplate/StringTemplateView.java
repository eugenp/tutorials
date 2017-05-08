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

        Resource templateResource = getApplicationContext().getResource(getUrl());

        //! TODO
        //! The group gets created (and hence other potential operations repeated needlessly) with each view
        //! rendering => for a production-ready solution, further customization and/or design is necessary!

        // Use $ sign to delimit ST expressions since we are generating HTML (which makes use of <, etc.)
        ST template =
            new STRawGroupDir(templateResource.getFile().getParent(), "UTF-8", '$', '$').getInstanceOf(getBeanName());
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            template.add(entry.getKey(), entry.getValue());
        }

        PrintWriter writer = response.getWriter();
        writer.print(template.render());
        writer.flush();
        writer.close();
    }
}
