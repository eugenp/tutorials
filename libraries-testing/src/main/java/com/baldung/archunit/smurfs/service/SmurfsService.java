/**
 * 
 */
package com.baldung.archunit.smurfs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baldung.archunit.smurfs.persistence.SmurfsRepository;
import com.baldung.archunit.smurfs.persistence.domain.Smurf;
import com.baldung.archunit.smurfs.service.dto.SmurfDTO;

/**
 * @author Philippe
 *
 */
@Component
public class SmurfsService {
    
    private SmurfsRepository repository;
    
    public SmurfsService(SmurfsRepository repository) {
        this.repository = repository;
    }
    
    public List<SmurfDTO> findAll() {
        
        return repository.findAll()
          .stream()
          .map(SmurfsService::toDTO)
          .collect(Collectors.toList());
    }
    
    
    public static SmurfDTO toDTO(Smurf smurf) {
        return new SmurfDTO(smurf.getName(),smurf.isComic(), smurf.isCartoon());
    }

}
