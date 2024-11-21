package com.baeldung.envers.customrevision.service;


import com.baeldung.envers.customrevision.domain.CustomRevisionEntity;
import com.baeldung.envers.customrevision.domain.Pet;
import com.baeldung.envers.customrevision.domain.PetHistoryEntry;
import com.baeldung.envers.customrevision.repository.OwnerRepository;
import com.baeldung.envers.customrevision.repository.PetRepository;
import com.baeldung.envers.customrevision.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private final OwnerRepository ownersRepo;
    private final PetRepository petsRepo;
    private final SpeciesRepository speciesRepo;

    /**
     * Register a new pet for adoption. Initially, this pet will have no name or owner.
     * @param speciesName
     * @return The assigned UUID for this pet
     */
    public UUID registerForAdoption( String speciesName) {

        var species = speciesRepo.findByName(speciesName)
          .orElseThrow(() -> new IllegalArgumentException("Unknown Species: " + speciesName));

        var pet = new Pet();
        pet.setSpecies(species);
        pet.setUuid(UUID.randomUUID());
        petsRepo.save(pet);
        return pet.getUuid();
    }


    public List<Pet> findPetsForAdoption(String speciesName) {
        var species = speciesRepo.findByName(speciesName)
          .orElseThrow(() -> new IllegalArgumentException("Unknown Species: " + speciesName));

        return petsRepo.findPetsByOwnerNullAndSpecies(species);
    }

    public Pet adoptPet(UUID petUuid, String ownerName, String petName) {

        var newOwner = ownersRepo.findByName(ownerName)
          .orElseThrow(() -> new IllegalArgumentException("Unknown owner"));

        var pet = petsRepo.findPetByUuid(petUuid)
          .orElseThrow(() -> new IllegalArgumentException("Unknown pet"));

        if ( pet.getOwner() != null) {
            throw new IllegalArgumentException("Pet already adopted");
        }

        pet.setOwner(newOwner);
        pet.setName(petName);
        petsRepo.save(pet);
        return pet;
    }

    public Pet returnPet(UUID petUuid) {
        var pet = petsRepo.findPetByUuid(petUuid)
          .orElseThrow(() -> new IllegalArgumentException("Unknown pet"));

        pet.setOwner(null);
        petsRepo.save(pet);
        return pet;
    }


    public List<PetHistoryEntry> listPetHistory(UUID petUuid) {

        var pet = petsRepo.findPetByUuid(petUuid)
          .orElseThrow(() -> new IllegalArgumentException("No pet with UUID '" + petUuid + "' found"));

        return petsRepo.findRevisions(pet.getId()).stream()
          .map(r -> {
              CustomRevisionEntity rev = r.getMetadata().getDelegate();
              return new PetHistoryEntry(r.getRequiredRevisionInstant(),
                r.getMetadata().getRevisionType(),
                r.getEntity().getUuid(),
                r.getEntity().getSpecies().getName(),
                r.getEntity().getName(),
                r.getEntity().getOwner() != null ? r.getEntity().getOwner().getName() : null,
                rev.getRemoteHost(),
                rev.getRemoteUser());
          })
          .toList();

    }


}
