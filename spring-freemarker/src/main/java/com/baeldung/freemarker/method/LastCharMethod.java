package com.baeldung.freemarker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.util.StringUtils;

import java.util.List;

public class LastCharMethod implements TemplateMethodModelEx {
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() != 1 || StringUtils.isEmpty(arguments.get(0)))
            throw new TemplateModelException("Wrong arguments!");
        String argument = arguments.get(0).toString();
        return argument.charAt(argument.length() - 1);
    }
}
