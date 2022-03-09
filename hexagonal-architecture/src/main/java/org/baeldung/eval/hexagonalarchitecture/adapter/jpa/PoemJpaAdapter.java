package org.baeldung.eval.hexagonalarchitecture.adapter.jpa;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import org.baeldung.eval.hexagonalarchitecture.adapter.jpa.entity.PoemEntity;
import org.baeldung.eval.hexagonalarchitecture.adapter.jpa.repository.PoemJPARepository;
import org.baeldung.eval.hexagonalarchitecture.data.Poem;
import org.baeldung.eval.hexagonalarchitecture.excpetion.PoemNotFoundException;
import org.baeldung.eval.hexagonalarchitecture.port.output.persistence.PoemPersistencePort;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
public class PoemJpaAdapter implements PoemPersistencePort{

    private final PoemJPARepository poemJPARepository;

    public PoemJpaAdapter(PoemJPARepository jpaRepository){
        this.poemJPARepository = jpaRepository;
    }
    @Override
    public void addPoem(Poem poemDTO) {
        PoemEntity poem = poemJPARepository.findByTitle(poemDTO.getTitle());
        if(poem==null){
            final PoemEntity poemEntity = getPoemEntity(poemDTO);
            poemJPARepository.save(poemEntity);   
        }
    }

    @Override
    public void removePoem(Poem poemDTO) {
        poemJPARepository.deleteAllByTitle(poemDTO.getTitle());
        
    }

    @Override
    public void updatePoem(Poem poemDTO) {
        PoemEntity poem = poemJPARepository.findByTitle(poemDTO.getTitle());
        if(Objects.isNull(poem)){
            addPoem(poemDTO);
            return;
        }
        poem.setAuthor(poemDTO.getAuthor());
        poem.setTitle(poemDTO.getAuthor());
        poemJPARepository.save(poem);
    }

    @Override
    public List<Poem> getAllPoems() {
       return poemJPARepository.findAll().stream().map(this::getPoemDTO).toList();
    }

    @SneakyThrows
    @Override
    public Poem getPoemById(UUID poemId) {
        return getPoemDTO(poemJPARepository.findById(poemId).orElseThrow((Supplier<Throwable>) () -> new PoemNotFoundException(poemId)));
    }
    

    private PoemEntity getPoemEntity(Poem poemDTO) {
        return PoemEntity.builder()
                .author(poemDTO.getAuthor())
                .title(poemDTO.getTitle())
                .build();
    }

    private Poem getPoemDTO(PoemEntity poemEntity) {
        return Poem.builder()
                .author(poemEntity.getAuthor())
                .title(poemEntity.getTitle())
                .build();
    }

}
