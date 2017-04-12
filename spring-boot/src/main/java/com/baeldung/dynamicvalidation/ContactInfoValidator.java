package com.baeldung.dynamicvalidation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.dynamicvalidation.dao.ContactInfoExpressionRepository;
import com.baeldung.dynamicvalidation.model.ContactInfoExpression;

public class ContactInfoValidator implements ConstraintValidator<ContactInfo, String> {

    @Autowired
    private ContactInfoExpressionRepository expressionRepository;

    @Override
    public void initialize(final ContactInfo contactInfo) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        String expressionType = System.getProperty("contactInfoType");
        System.out.println(expressionType);
        final ContactInfoExpression expression = expressionRepository.findOne(expressionType);
        if (expression != null) {
            final String pattern = expression.getPattern();
            if (Pattern.matches(pattern, value))
                return true;
        }
        return false;
    }

}
