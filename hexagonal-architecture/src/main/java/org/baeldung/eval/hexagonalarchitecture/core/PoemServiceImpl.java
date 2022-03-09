package org.baeldung.eval.hexagonalarchitecture.core;

import java.util.List;
import java.util.UUID;

import org.baeldung.eval.hexagonalarchitecture.data.Poem;
import org.baeldung.eval.hexagonalarchitecture.port.output.persistence.PoemPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PoemServiceImpl implements PoemService{
   
    private final PoemPersistencePort poemPersistencePort;

    public PoemServiceImpl(PoemPersistencePort poemPersistencePort) {
        this.poemPersistencePort = poemPersistencePort;
    }

    @Override
    @Transactional
    public void addPoems(Poem poemDTO) {
        poemPersistencePort.addPoem(poemDTO);
    }

    @Override
    @Transactional
    public void removePoems(Poem poemDTO) {
        poemPersistencePort.removePoem(poemDTO);
    }
    
    @Override
    public List<Poem> getAllPoems() {
        return poemPersistencePort.getAllPoems();
    }

    @Override
    public void updatePoems(Poem poemDTO) {
        poemPersistencePort.updatePoem(poemDTO);
    }

   

    @Override
    public Poem getPoemById(UUID poemId) {
        return poemPersistencePort.getPoemById(poemId);
    }

}
