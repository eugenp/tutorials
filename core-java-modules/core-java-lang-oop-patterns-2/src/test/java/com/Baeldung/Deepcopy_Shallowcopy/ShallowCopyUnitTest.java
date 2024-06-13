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
    void copyShallowOjectAndPassingTheValues_thenObjectShouldNotBeTheSame() throws CloneNotSupportedException {
        MedicalTestResult medicalTest = new MedicalTestResult("BP", "TEST-H/05", 20.0);
        Patient patient1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
        Patient copyShallowPatient1 = new Patient(patient1.getpName(), patient1.getpID(), patient1.getpGender(), patient1.getpAge(), medicalTest);
        assertThat(copyShallowPatient1).isNotSameAs(patient1);
    }

    @Test
    void whenModifyingTheOriginalObject_thenTheCopyObjectShouldChange() throws CloneNotSupportedException {
        MedicalTestResult medicalTest = new MedicalTestResult("BP", "TEST-H/05", 20.0);
        Patient patient1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
        Patient copyShallowPatient1 = new Patient(patient1.getpName(), patient1.getpID(), patient1.getpGender(), patient1.getpAge(), medicalTest);
        medicalTest.setTestName("Urine");
        assertThat(copyShallowPatient1.getMedical_test().getTestName()).isEqualTo(medicalTest.getTestName());
    }

    @Test
    void copyShallowOjectWithSerializable() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            MedicalTestResult medicalTest = new MedicalTestResult("BP", "TEST-H/05", 20.0);
            Patient patient1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
            out.writeObject(patient1);
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