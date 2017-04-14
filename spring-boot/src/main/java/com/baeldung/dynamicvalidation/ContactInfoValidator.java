package com.baeldung.dynamicvalidation;

import com.baeldung.dynamicvalidation.dao.ContactInfoExpressionRepository;
import com.baeldung.dynamicvalidation.model.ContactInfoExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

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
        if (StringUtils.isEmptyOrWhitespace(expressionType)) {
            return false;
        }

        return expressionRepository
          .findOne(expressionType)
          .map(ContactInfoExpression::getPattern)
          .map(p -> Pattern.matches(p, value))
          .orElse(false);
    }

}
