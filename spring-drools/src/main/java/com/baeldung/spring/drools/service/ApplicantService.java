package com.baeldung.spring.drools.service;

import com.baeldung.spring.drools.model.Applicant;
import com.baeldung.spring.drools.model.SuggestedRole;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantService {

    private final KieContainer kieContainer;

    @Autowired
    public ApplicantService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
    }


    public SuggestedRole suggestARoleForApplicant(Applicant applicant,SuggestedRole suggestedRole){
        KieSession kieSession = kieContainer.newKieSession("ApplicantSession");
        kieSession.insert(applicant);
        kieSession.setGlobal("suggestedRole",suggestedRole);
        kieSession.fireAllRules();
        System.out.println(suggestedRole.getRole());
        return  suggestedRole;

    }


}
