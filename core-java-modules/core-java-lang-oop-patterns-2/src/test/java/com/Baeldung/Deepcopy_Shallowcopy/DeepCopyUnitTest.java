package com.Baeldung.Deepcopy_Shallowcopy;
import java.io.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {

    @Test
    void whenModifyingTheObject_thenTheCopyShouldNotChange() throws CloneNotSupportedException {
        MedicalTestResult medicalTest = new MedicalTestResult("BP","TEST-H/05",20.0);
        Patient patient1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
        Patient shallowCopyPatient1 = (Patient) patient1.clone();
        MedicalTestResult deepCopiedMedical = shallowCopyPatient1.getMedical_test();
        medicalTest.setTestName("Sugar");
        assertThat(deepCopiedMedical.getTestName()).isNotEqualTo(patient1.getMedical_test().getTestName());
    }

    @Test
    void copyDeepObjectWithSerializable() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            MedicalTestResult medicalTest = new MedicalTestResult("BP","TEST-H/05",20.0);
            Patient patient1 = new Patient("James Balli", 101, "Male", 36, medicalTest);
            out.writeObject(patient1);
            out.flush();
            out.close();

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            Patient deepClonedPatient1 = (Patient) in.readObject();
            in.close();

            MedicalTestResult clonedMedicalTest = deepClonedPatient1.getMedical_test();
            medicalTest.setTestName("Blood");
            assertThat(clonedMedicalTest).isNotSameAs(deepClonedPatient1);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
