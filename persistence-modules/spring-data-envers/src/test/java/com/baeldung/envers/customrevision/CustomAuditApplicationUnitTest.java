package com.baeldung.envers.customrevision;

import com.baeldung.envers.customrevision.domain.Owner;
import com.baeldung.envers.customrevision.domain.PetHistoryEntry;
import com.baeldung.envers.customrevision.domain.Species;
import com.baeldung.envers.customrevision.repository.OwnerRepository;
import com.baeldung.envers.customrevision.repository.SpeciesRepository;
import com.baeldung.envers.customrevision.service.AdoptionService;
import com.baeldung.envers.customrevision.service.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class CustomAuditApplicationUnitTest {

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

        List<PetHistoryEntry> kittyHistory = adoptionService.listPetHistory(kitty.getUuid());
        assertNotNull(kittyHistory);
        assertTrue(kittyHistory.size() > 0 , "kitty should have a history");
        for (PetHistoryEntry e : kittyHistory) {
            log.info("Entry: {}", e);
        }

    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        Supplier<Optional<RequestInfo>> requestInfoSupplier() {

            return () -> Optional.of(new RequestInfo("example.com", "thomas"));

        }

        @Bean
        CommandLineRunner populateShelter(SpeciesRepository speciesRepo, OwnerRepository ownerRepo) {

            return (args) -> {
                // Add species
                speciesRepo.saveAll(List.of(
                  Species.forName("dog"),
                  Species.forName("cat"),
                  Species.forName("chinchilla")));

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