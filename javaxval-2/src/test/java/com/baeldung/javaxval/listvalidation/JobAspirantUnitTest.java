package com.baeldung.javaxval.listvalidation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JobAspirantUnitTest {
    private static Validator validator;
    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Test
    public void givenJobLevelJunior_whenInValidMinExperience_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Junior", "John Adam", "2025-12-31", 3, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Junior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("experience");
            assertThat(action.getMessage()).isEqualTo("Years of experience cannot be less than 5 Years");
        });
    }
    @Test
    public void givenJobLevelMidSenior_whenInvalidMinExperience_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("MidSenior", "John Adam", "2025-12-31", 8, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, MidSenior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("experience");
            assertThat(action.getMessage()).isEqualTo("Years of experience cannot be less than 10 Years");
        });
    }
    @Test
    public void givenJobLevelSenior_whenInvalidMinExperience_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Senior", "John Adam", "2025-12-31", 13, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Senior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("experience");
            assertThat(action.getMessage()).isEqualTo("Years of experience cannot be less than 15 Years");
        });
    }
    @Test
    public void givenJobLevelJunior_whenInValidMaxExperience_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Junior", "John Adam", "2025-12-31", 11, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Junior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("experience");
            assertThat(action.getMessage()).isEqualTo("Years of experience cannot be more than 10 Years");
        });
    }
    @Test
    public void givenJobLevelMidSenior_whenInvalidMaxExperience_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("MidSenior", "John Adam", "2025-12-31", 16, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, MidSenior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("experience");
            assertThat(action.getMessage()).isEqualTo("Years of experience cannot be more than 15 Years");
        });
    }
    @Test
    public void givenJobLevelSenior_whenInvalidMaxExperience_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Senior", "John Adam", "2025-12-31", 23, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Senior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("experience");
            assertThat(action.getMessage()).isEqualTo("Years of experience cannot be more than 20 Years");
        });
    }
    @Test
    public void whenInvalidName_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Senior", "John  Adam", "2025-12-31", 17, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Senior.class, AllLevels.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("name");
            assertThat(action.getMessage()).isEqualTo("Name should not contain consecutive spaces");
        });
    }
    @Test
    public void givenJuniorLevel_whenInvalidAgreement_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Junior", "John Adam", "2025-12-31", 7, false);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Junior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("agreement");
            assertThat(action.getMessage()).isEqualTo("Terms and Conditions consent missing for Junior Level Job");
        });
    }
    @Test
    public void givenSeniorLevel_whenInvalidAgreement_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Senior", "John Adam", "2025-12-31", 17, false);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Senior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("agreement");
            assertThat(action.getMessage()).isEqualTo("Terms and Conditions consent missing for Senior Level Job");
        });
    }
    @Test
    public void givenJobLevelMidSenior_whenInvalidPassport_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("MidSenior", "John Adam", "2021-12-31", 12, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, MidSenior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("passportExpiryDate");
            assertThat(action.getMessage()).isEqualTo("Active passport is mandatory for Mid-Senior Level Job");
        });
    }
    @Test
    public void givenJobLevelSenior_whenInvalidPassport_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Senior", "John Adam", "2021-12-31", 18, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Senior.class);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> {
            assertThat(action.getPropertyPath().toString()).isEqualTo("passportExpiryDate");
            assertThat(action.getMessage()).isEqualTo("Active passport is mandatory for Senior Level Job");
        });
    }
    @Test
    public void givenJobLevelSenior_whenAllFieldsValid_thenNoErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Senior", "John Adam", "2025-12-31", 17, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Senior.class, AllLevels.class);
        assertThat(violations.size()).isEqualTo(0);
    }
    @Test
    public void givenJobLevelMidSenior_whenAllFieldsValid_thenNoErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("MidSenior", "John Adam", "2025-12-31", 12, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, MidSenior.class, AllLevels.class);
        assertThat(violations.size()).isEqualTo(0);
    }
    @Test
    public void givenJobLevelJunior_whenAllFieldsValid_thenNoErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Junior", "John Adam", "2025-12-31", 7, true);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Junior.class, AllLevels.class);
        assertThat(violations.size()).isEqualTo(0);
    }
    @Test
    public void givenJobLevelJunior_whenAllFieldsInValid_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Junior", " John Adam", "2022-12-31", 3, false);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Junior.class, AllLevels.class);
        assertThat(violations.size()).isEqualTo(4);
        violations.forEach(action -> {
            String fieldName = action.getPropertyPath().toString();
            switch(fieldName) {
                case "name":
                    assertThat(action.getMessage()).isEqualTo("Name should not start with space");
                    break;
                case "passportExpiryDate":
                    assertThat(action.getMessage()).isEqualTo("Active passport is mandatory for Junior Level Job");
                    break;
                case "experience":
                    assertThat(action.getMessage()).isEqualTo("Years of experience cannot be less than 5 Years");
                    break;
                case "agreement":
                    assertThat(action.getMessage()).isEqualTo("Terms and Conditions consent missing for Junior Level Job");
                    break;
            }
        });
    }
    @Test
    public void givenJobLevelSenior_whenAllFieldsInValid_thenExpectErrors() throws ParseException {
        JobAspirant jobAspirant = getJobAspirant("Senior", "John Adam ", "2022-12-31", 12, false);
        Set<ConstraintViolation<JobAspirant>> violations = validator.validate(jobAspirant, Senior.class, AllLevels.class);
        assertThat(violations.size()).isEqualTo(4);
        violations.forEach(action -> {
            String fieldName = action.getPropertyPath().toString();
            switch(fieldName) {
                case "name":
                    assertThat(action.getMessage()).isEqualTo("Name should not end with space");
                    break;
                case "passportExpiryDate":
                    assertThat(action.getMessage()).isEqualTo("Active passport is mandatory for Senior Level Job");
                    break;
                case "experience":
                    assertThat(action.getMessage()).isEqualTo("Years of experience cannot be less than 15 Years");
                    break;
                case "agreement":
                    assertThat(action.getMessage()).isEqualTo("Terms and Conditions consent missing for Senior Level Job");
                    break;
            }
        });
    }
    private JobAspirant getJobAspirant(String jobLevel, String name, String passportExpDate, int exp, boolean agmt) throws ParseException {
        JobAspirant jobAspirant = new JobAspirant();
        jobAspirant.setName(name);
        jobAspirant.setPassportExpiryDate(convertStringToDate(passportExpDate));
        jobAspirant.setJobLevel(jobLevel);
        jobAspirant.setExperience(exp);
        jobAspirant.setAgreement(agmt);
        return jobAspirant;
    }
    private Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(date);
    }
}
