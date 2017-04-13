package com.baeldung.dynamicvalidation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.util.StringUtils;

import com.baeldung.dynamicvalidation.dao.ContactInfoExpressionRepository;
import com.baeldung.dynamicvalidation.model.ContactInfoExpression;

public class ContactInfoValidator implements ConstraintValidator<ContactInfo, String> {

    @Autowired
    private ContactInfoExpressionRepository expressionRepository;

    @Value("${contactInfoType}")
    String expressionType;

    @Override
    public void initialize(final ContactInfo contactInfo) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (!StringUtils.isEmptyOrWhitespace(expressionType)) {
            final String pattern = expressionRepository.findOne(expressionType).map(ContactInfoExpression::getPattern).orElse("");
            if (Pattern.matches(pattern, value))
                return true;
        }
        return false;
    }

}
