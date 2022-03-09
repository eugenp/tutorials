package org.baeldung.eval.hexagonalarchitecture.core;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.UUID;

import org.baeldung.eval.hexagonalarchitecture.data.Poem;
import org.baeldung.eval.hexagonalarchitecture.port.output.persistence.PoemPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PoemServiceImplTest {
    
    
    @InjectMocks
    private PoemServiceImpl poemServiceMock;

    @Mock
    private PoemPersistencePort poemPersistencePort;

    @Mock
    private List<Poem> mockPoemDTOList;

    @Test
    void testAddPoems_whenAddPoem_thenAddPortCalled() {
        final Poem testPoem = Poem.builder().build();
        poemServiceMock.addPoems(testPoem);
        verify(poemPersistencePort,only()).addPoem(testPoem);
    }

    
    @Test
    void testGetAllPoems() {
        poemServiceMock.getAllPoems();
        verify(poemPersistencePort,only()).getAllPoems();
    }

    @Test
    void testGetPoemById() {
        UUID poemId = UUID.randomUUID();
        poemServiceMock.getPoemById(poemId);
        verify(poemPersistencePort,only()).getPoemById(poemId);
    }

    @Test
    void testRemovePoems() {
        final Poem testPoem = Poem.builder().build();
        poemServiceMock.removePoems(testPoem);
        verify(poemPersistencePort,only()).removePoem(testPoem);
    }

    @Test
    void testUpdatePoems() {
        final Poem testPoem = Poem.builder().build();
        poemServiceMock.updatePoems(testPoem);
        verify(poemPersistencePort,only()).updatePoem(testPoem);
    }

}
