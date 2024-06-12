package com.Baeldung.Deepcopy_Shallowcopy;
import java.io.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {

    @Test
    void copyOfDeepOjectAndModifyingWithCopyMethod() throws CloneNotSupportedException {
        MedicalTestResult medicalTest = new MedicalTestResult("BP","TEST-H/05",20.0);
        Patient p1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
        Patient copyP1 = (Patient) p1.clone();
        MedicalTestResult copiedMedical = copyP1.getMedical_test();
        System.out.println(copiedMedical.getTestName());
        assertThat(copyP1.getMedical_test().getTestName()).isSameAs(copiedMedical.getTestName());
    }

    @Test
    void copyOfDeepOjectAndModifyingWithCloneMethod() throws CloneNotSupportedException {
        MedicalTestResult medicalTest = new MedicalTestResult("BP","TEST-H/05",20.0);
        Patient p1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
        Patient copyP1 = (Patient) p1.clone();
        MedicalTestResult copiedMedical = copyP1.getMedical_test();
        copiedMedical.setTestName("Sugar");
        assertThat(copiedMedical.getTestName()).isNotSameAs(copyP1.getMedical_test().getTestName());
    }

    @Test
    void copyDeepOjectWithSerializable() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            MedicalTestResult medicalTest = new MedicalTestResult("BP","TEST-H/05",20.0);
            Patient p1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
            out.writeObject(p1);
            out.flush();
            out.close();

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            Patient clonedP1 = (Patient) in.readObject();
            in.close();

            MedicalTestResult clonedMedicalTest = clonedP1.getMedical_test();
            assertThat(clonedMedicalTest.getTestName()).isEqualTo(clonedP1.getMedical_test().getTestName());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
