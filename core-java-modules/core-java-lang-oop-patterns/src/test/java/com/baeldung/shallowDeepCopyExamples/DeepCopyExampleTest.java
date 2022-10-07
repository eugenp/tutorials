package com.baeldung.shallowDeepCopyExamples;

import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import com.baeldung.shallowDeepCopyExamples.pojo.Candidate;
import com.baeldung.shallowDeepCopyExamples.pojo.ContactInfo;

public class DeepCopyExampleTest {
    DeepCopyExample deepCopyExample = new DeepCopyExample();

    @Test
    public void whenDeepCopyingObjects_thenOriginalObjectIsUnchanged() throws CloneNotSupportedException {
        Candidate firstCandidate = getTestCandidate();
        Candidate secondCandidate = deepCopyExample.copyCandidateObject(firstCandidate);
        assertNotEquals(firstCandidate, secondCandidate);
        assertNotEquals(firstCandidate.getLastName(), secondCandidate.getLastName());
        assertNotEquals(firstCandidate.getContactInfo().getPhoneNumber(), secondCandidate.getContactInfo().getPhoneNumber());
    }

    private Candidate getTestCandidate() {
        return Candidate.builder()
                        .firstName("Looney")
                        .lastName("Tunes")
                        .age(5)
                        .contactInfo(ContactInfo.builder().countryCode(80).phoneNumber("9839012345").build())
                        .build();
    }
}