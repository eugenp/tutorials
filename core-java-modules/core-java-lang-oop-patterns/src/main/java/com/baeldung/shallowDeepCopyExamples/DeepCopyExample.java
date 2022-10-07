package com.baeldung.shallowDeepCopyExamples;

import com.baeldung.shallowDeepCopyExamples.pojo.Candidate;

public class DeepCopyExample {

    public Candidate copyCandidateObject(Candidate firstCandidate) throws CloneNotSupportedException {
        Candidate secondCandidate = firstCandidate.clone();
        secondCandidate.setLastName("Parker");
        secondCandidate.setAge(25);
        secondCandidate.getContactInfo().setPhoneNumber("9839098390");
        return secondCandidate;
    }
}
