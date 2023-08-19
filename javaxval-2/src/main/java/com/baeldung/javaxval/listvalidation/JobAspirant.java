package com.baeldung.javaxval.listvalidation;

import jakarta.validation.constraints.*;

import java.util.Date;

public class JobAspirant {
    @Size.List({
            @Size(min = 5, message = "Name should have at least 5 characters", groups = AllLevels.class),
            @Size(max = 20, message = "Name should have at most 20 characters", groups = AllLevels.class)
    })
    @Pattern.List({
            @Pattern(regexp = "^[\\p{Alpha} ]*$", message = "Name should contain only alphabets and space", groups = AllLevels.class),
            @Pattern(regexp = "^[^\\s].*$", message = "Name should not start with space", groups = AllLevels.class),
            @Pattern(regexp = "^.*[^\\s]$", message = "Name should not end with space", groups = AllLevels.class),
            @Pattern(regexp = "^((?!  ).)*$", message = "Name should not contain consecutive spaces", groups = AllLevels.class),
            @Pattern(regexp = "^[^a-z].*$", message = "Name should not start with a lower case character", groups = AllLevels.class)
    })
    private String name;

    @Min.List({
            @Min(value = 15, message = "Years of experience cannot be less than 15 Years", groups = Senior.class),
            @Min(value = 10, message = "Years of experience cannot be less than 10 Years", groups = MidSenior.class),
            @Min(value = 5, message = "Years of experience cannot be less than 5 Years", groups = Junior.class)
    })
    @Max.List({
            @Max(value = 20, message = "Years of experience cannot be more than 20 Years", groups = Senior.class),
            @Max(value = 15, message = "Years of experience cannot be more than 15 Years", groups = MidSenior.class),
            @Max(value = 10, message = "Years of experience cannot be more than 10 Years", groups = Junior.class)
    })
    private Integer experience;

    @AssertTrue.List({
            @AssertTrue(message = "Terms and Conditions consent missing for Senior Level Job", groups = Senior.class),
            @AssertTrue(message = "Terms and Conditions consent missing for Mid-Senior Level Job", groups = MidSenior.class),
            @AssertTrue(message = "Terms and Conditions consent missing for Junior Level Job", groups = Junior.class)
    })
    private Boolean agreement;

    @Future.List({
            @Future(message = "Active passport is mandatory for Senior Level Job", groups = Senior.class),
            @Future(message = "Active passport is mandatory for Mid-Senior Level Job", groups = MidSenior.class),
            @Future(message = "Active passport is mandatory for Junior Level Job", groups = Junior.class)
    })
    private Date passportExpiryDate;

    @Pattern.List({
                    @Pattern(regexp = "^(Senior)$", message = "Job level should be Senior"
                            ,flags = Pattern.Flag.CASE_INSENSITIVE, groups = Senior.class),
                    @Pattern(regexp = "^(MidSenior)$", message = "Job level should be MidSenior"
                            ,flags = Pattern.Flag.CASE_INSENSITIVE, groups = MidSenior.class),
                    @Pattern(regexp = "^(Junior)$", message = "Job level should be Junior"
                            ,flags = Pattern.Flag.CASE_INSENSITIVE, groups = Junior.class)
    })
    private String jobLevel;

    public String getJobLevel() {
        return jobLevel;
    }

    public String getName() {
        return name;
    }

    public Boolean getAgreement() {
        return agreement;
    }
    public Date getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setAgreement(Boolean agreement) {
        this.agreement = agreement;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public void setPassportExpiryDate(Date passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
