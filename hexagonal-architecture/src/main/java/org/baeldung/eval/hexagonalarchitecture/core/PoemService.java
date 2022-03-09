package org.baeldung.eval.hexagonalarchitecture.core;

import java.util.List;
import java.util.UUID;

import org.baeldung.eval.hexagonalarchitecture.data.Poem;

public interface PoemService {
    public void addPoems(Poem poemDTO);

    public void removePoems(Poem poemDTO);

    public void updatePoems(Poem poemDTO);

    public List<Poem> getAllPoems();

    public Poem getPoemById(UUID poemId);
}
