package org.baeldung.eval.hexagonalarchitecture.port.output.persistence;

import java.util.List;
import java.util.UUID;

import org.baeldung.eval.hexagonalarchitecture.data.Poem;

public interface PoemPersistencePort {
    public void addPoem(Poem poemDTO);

    public void removePoem(Poem poemDTO);

    public void updatePoem(Poem poemDTO);

    public List<Poem> getAllPoems();

    public Poem getPoemById(UUID poemId);
}
