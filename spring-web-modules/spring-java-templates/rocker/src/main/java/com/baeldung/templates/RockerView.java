package com.baeldung.templates;

import com.fizzed.rocker.BindableRockerModel;
import com.fizzed.rocker.Rocker;
import com.fizzed.rocker.RockerOutput;
import com.fizzed.rocker.TemplateBindException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;

import java.util.Map;

public class RockerView implements View {
    private final String viewName;

    public RockerView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BindableRockerModel template = Rocker.template(viewName);
        for (Map.Entry<String, ?> entry : model.entrySet()) {
            try {
                template.bind(entry.getKey(), entry.getValue());
            } catch (TemplateBindException e) {
                // Ignore
            }
        }
        RockerOutput output = template.render();

        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.getWriter().write(output.toString());
    }
}
