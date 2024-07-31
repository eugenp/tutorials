package com.baeldung.attributevaluegenerator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.bytebuddy.utility.RandomString;

@SpringBootTest(classes = Application.class, properties = "spring.sql.init.data-locations")
class WizardAttributeValueGeneratorIntegrationTest {

    @Autowired
    private WizardRepository wizardRepository;

    @Test
    void whenNewWizardRecordSaved_thenHouseGetsSorted() {
        Wizard wizard = new Wizard();
        wizard.setId(UUID.randomUUID());
        wizard.setName(RandomString.make());

        Wizard savedWizard = wizardRepository.save(wizard);

        assertThat(savedWizard.getHouse())
            .isNotBlank()
            .isIn("Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin");
    }

    @Test
    void whenWizardRecordUpdated_thenUpdatedAtTimestampRefreshed() {
        Wizard wizard = new Wizard();
        wizard.setId(UUID.randomUUID());
        wizard.setName(RandomString.make());

        Wizard savedWizard = wizardRepository.save(wizard);
        LocalDateTime initialUpdatedAtTimestamp = savedWizard.getUpdatedAt();

        savedWizard.setName(RandomString.make());
        Wizard updatedWizard = wizardRepository.save(savedWizard);

        assertThat(updatedWizard.getUpdatedAt())
            .isAfter(initialUpdatedAtTimestamp);
    }

    @Test
    void whenNewWizardRecordSaved_thenSpellPowerGenerated() {
        Wizard wizard = new Wizard();
        wizard.setId(UUID.randomUUID());
        wizard.setName(RandomString.make());

        Wizard savedWizard = wizardRepository.save(wizard);

        assertThat(savedWizard.getSpellPower())
            .isNotNull()
            .isGreaterThanOrEqualTo(50);
    }

}
