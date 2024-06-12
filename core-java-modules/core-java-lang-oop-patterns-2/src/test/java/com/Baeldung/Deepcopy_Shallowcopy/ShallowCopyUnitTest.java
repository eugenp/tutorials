package com.Baeldung.Deepcopy_Shallowcopy;
import java.io.*;

import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


public class ShallowCopyUnitTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void copyShallowOjectAndPassingTheValues_ThenObjectShouldNotBeTheSame() throws CloneNotSupportedException {
        MedicalTestResult medicalTest = new MedicalTestResult("BP", "TEST-H/05", 20.0);
        Patient p1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
        Patient copyShallowP1 = new Patient(p1.getpName(), p1.getpID(), p1.getpGender(), p1.getpAge(), medicalTest);
        assertThat(copyShallowP1.getpName()).isNotSameAs(p1.getpName());
    }

    @Test
    void ModifyingTheOriginalObject_ThenTheObjectShouldAlsoChange() throws CloneNotSupportedException {
        MedicalTestResult medicalTest = new MedicalTestResult("BP", "TEST-H/05", 20.0);
        Patient p1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
        Patient copyShallowP1 = new Patient(p1.getpName(), p1.getpID(), p1.getpGender(), p1.getpAge(), medicalTest);
        p1.setpName("Steven Dremo");
        assertThat(copyShallowP1.getpName()).isNotEqualTo(p1.getpName());
    }


    @Test
    void CopyOfShallowOjectAndPassingTheValuesUsingCloneMethod_TheObjectShouldNotAlsoBeTheSame() throws CloneNotSupportedException {
        MedicalTestResult medicalTest = new MedicalTestResult("BP", "TEST-H/05", 20.0);
        Patient p1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
        Patient copyShallowP1 = (Patient) p1.clone();
        medicalTest.setTestName("Genetic");
        assertThat(copyShallowP1.getMedical_test().getTestName()).isNotEqualTo(medicalTest.getTestName());
    }


    @Test
    void copyShallowOjectWithSerializable() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            MedicalTestResult medicalTest = new MedicalTestResult("BP", "TEST-H/05", 20.0);
            Patient p1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
            out.writeObject(p1);
            out.flush();
            out.close();

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            Patient clonedP1 = (Patient) in.readObject();
            in.close();

            MedicalTestResult clonedMedicalTest = clonedP1.getMedical_test();
            assertThat(clonedMedicalTest.getTestName()).isSameAs(clonedP1.getMedical_test().getTestName());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}