package com.baeldung.spring.drools.service;

import com.baeldung.spring.drools.Application;
import com.baeldung.spring.drools.DroolConfiguration;
import com.baeldung.spring.drools.model.Applicant;
import com.baeldung.spring.drools.model.SuggestedRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DroolConfiguration.class})
public class ApplicantServiceIntegrationTest {

    @Autowired
    ApplicantService applicantService;

    @Test
    public void whenCriteriaMatching_ThenSuggestManagerRole(){
        Applicant applicant=new Applicant("Davis",37,1600000.0,11);
        SuggestedRole suggestedRole=new SuggestedRole();
        applicantService.suggestARoleForApplicant(applicant,suggestedRole);
        assertEquals("Manager",suggestedRole.getRole());
    }

    @Test
    public void whenCriteriaMatching_ThenSuggestSeniorDeveloperRole(){
        Applicant applicant=new Applicant("John",37,1200000.0,8);
        SuggestedRole suggestedRole=new SuggestedRole();
        applicantService.suggestARoleForApplicant(applicant,suggestedRole);
        assertEquals("Senior developer",suggestedRole.getRole());
    }
    @Test
    public void whenCriteriaMatching_ThenSuggestDeveloperRole(){
        Applicant applicant=new Applicant("Davis",37,800000.0,3);
        SuggestedRole suggestedRole=new SuggestedRole();
        applicantService.suggestARoleForApplicant(applicant,suggestedRole);
        assertEquals("Developer",suggestedRole.getRole());
    }
    @Test
    public void whenCriteriaNotMatching_ThenNoRole(){
        Applicant applicant=new Applicant("John",37,1200000.0,5);
        SuggestedRole suggestedRole=new SuggestedRole();
        applicantService.suggestARoleForApplicant(applicant,suggestedRole);
        assertNull(suggestedRole.getRole());
    }
}
