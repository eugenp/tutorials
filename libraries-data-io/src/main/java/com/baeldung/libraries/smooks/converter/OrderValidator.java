package com.baeldung.libraries.smooks.converter;

import org.milyn.Smooks;
import org.milyn.payload.JavaResult;
import org.milyn.payload.StringResult;
import org.milyn.validation.ValidationResult;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class OrderValidator {

    public ValidationResult validate(String path) throws IOException, SAXException {
        Smooks smooks = new Smooks(OrderValidator.class.getResourceAsStream("/smooks/smooks-validation.xml"));

        try {
            StringResult xmlResult = new StringResult();
            JavaResult javaResult = new JavaResult();
            ValidationResult validationResult = new ValidationResult();
            smooks.filterSource(new StreamSource(OrderValidator.class.getResourceAsStream(path)), xmlResult, javaResult, validationResult);
            return validationResult;
        } finally {
            smooks.close();
        }
    }
}
