package com.baeldung.dynamicvalidation;

import com.baeldung.dynamicvalidation.dao.ContactInfoExpressionRepository;
import com.baeldung.dynamicvalidation.model.ContactInfoExpression;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

<<<<<<< HEAD
import java.util.regex.Pattern;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.util.StringUtils;

import com.baeldung.dynamicvalidation.dao.ContactInfoExpressionRepository;
import com.baeldung.dynamicvalidation.model.ContactInfoExpression;
>>>>>>> e85e33f2d07978442e4e05de37c9c10f1bc5fe60

public class ContactInfoValidator implements ConstraintValidator<ContactInfo, String> {

    private static final Logger LOG = Logger.getLogger(ContactInfoValidator.class);

    @Autowired
    private ContactInfoExpressionRepository expressionRepository;

    @Value("${contactInfoType}")
    String expressionType;

<<<<<<< HEAD
    private String pattern;

=======
>>>>>>> e85e33f2d07978442e4e05de37c9c10f1bc5fe60
    @Override
    public void initialize(final ContactInfo contactInfo) {
        if (StringUtils.isEmptyOrWhitespace(expressionType)) {
            LOG.error("Contact info type missing!");
        } else {
            pattern = expressionRepository.findOne(expressionType)
                .map(ContactInfoExpression::getPattern)
                .get();
        }
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
<<<<<<< HEAD
        if (!StringUtils.isEmptyOrWhitespace(pattern)) {
            return Pattern.matches(pattern, value);
=======
        if (!StringUtils.isEmptyOrWhitespace(expressionType)) {
            final String pattern = expressionRepository.findOne(expressionType).map(ContactInfoExpression::getPattern).orElse("");
            if (Pattern.matches(pattern, value))
                return true;
>>>>>>> e85e33f2d07978442e4e05de37c9c10f1bc5fe60
        }
        LOG.error("Contact info pattern missing!");
        return false;
    }

}
