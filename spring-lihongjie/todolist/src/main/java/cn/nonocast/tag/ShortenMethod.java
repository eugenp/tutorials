package cn.nonocast.tag;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public class ShortenMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }

        String result = "";

        if(arguments.get(0) == null) return result;

        String source = (String)arguments.get(0).toString();

        if(source != null && source.length() > 20) {
            String begin = source.substring(0, 3);
            String end = source.substring(source.length() - 5, source.length());
            result =String.format("%s...%s", begin, end);
        }

        return result;
    }
}