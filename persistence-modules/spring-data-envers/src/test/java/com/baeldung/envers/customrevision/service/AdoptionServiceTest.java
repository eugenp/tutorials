package com.baeldung.envers.customrevision.service;

import com.baeldung.envers.customrevision.domain.Owner;
import com.baeldung.envers.customrevision.domain.PetLogInfo;
import com.baeldung.envers.customrevision.domain.Species;
import com.baeldung.envers.customrevision.repository.OwnerRepository;
import com.baeldung.envers.customrevision.repository.PetRepository;
import com.baeldung.envers.customrevision.repository.SpeciesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableJpaRepositories(
  basePackages = "com.baeldung.envers.customrevision",
  repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
class AdoptionServiceTest {

    @Autowired
    AdoptionService adoptionService;

    @Test
    void whenRegisterForAdoption_thenSuccess() {

        var generatedUuid = adoptionService.registerForAdoption("dog");
        assertNotNull(generatedUuid);

        var doggies = adoptionService.findPetsForAdoption("dog");
        assertNotNull(doggies);
        assertEquals(1,doggies.size(), "should have one dog for adoption");
        assertEquals("dog", doggies.get(0).getSpecies().getName());
        assertNull(doggies.get(0).getOwner());
    }

    @Test
    void whenAdoptPet_thenSuccess() {

        var petUuid = adoptionService.registerForAdoption("cat");
        var kitty = adoptionService.adoptPet(petUuid,"adam", "kitty");

        List<PetLogInfo> kittyHistory = adoptionService.listPetRevisions(kitty.getUuid());
        assertNotNull(kittyHistory);


    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        CommandLineRunner populateShelter(SpeciesRepository speciesRepo, OwnerRepository ownerRepo) {

            return (args) -> {
                // Add species
                speciesRepo.saveAll(List.of(
                  Species.valueOf("dog"),
                  Species.valueOf("cat"),
                  Species.valueOf("chinchilla")));

                // Add Owners
                ownerRepo.saveAll( List.of(
                  Owner.forName("adam"),
                  Owner.forName("mary"),
                  Owner.forName("phil")
                ));
            };
        }

    }
}