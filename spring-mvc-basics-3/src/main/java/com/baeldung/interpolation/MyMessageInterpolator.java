package com.baeldung.interpolation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.MessageInterpolator;
import java.util.Locale;

public class MyMessageInterpolator implements MessageInterpolator {

    private static Logger logger = LoggerFactory.getLogger(MyMessageInterpolator.class);

    private final MessageInterpolator defaultInterpolator;

    public MyMessageInterpolator(MessageInterpolator interpolator) {
        this.defaultInterpolator = interpolator;
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        messageTemplate = messageTemplate.toUpperCase();
        return defaultInterpolator.interpolate(messageTemplate, context, Locale.getDefault());
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        messageTemplate = messageTemplate.toUpperCase();
        return defaultInterpolator.interpolate(messageTemplate, context, locale);
    }
}
