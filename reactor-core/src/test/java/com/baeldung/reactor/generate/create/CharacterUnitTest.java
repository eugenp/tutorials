package com.baeldung.reactor.generate.create;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CharacterUnitTest {
    @Test
    public void whenGeneratingCharacters_thenCharactersAreProduced() {
        CharacterGenerator characterGenerator = new CharacterGenerator();
        Flux<Character> characterFlux = characterGenerator.generateCharacters().take(3);

        StepVerifier.create(characterFlux)
            .expectNext('a', 'b', 'c')
            .expectComplete()
            .verify();
    }
    
    @Test
    public void whenCreatingCharactersWithMultipleThreads_thenSequenceIsProducedAsynchronously() throws InterruptedException {
        CharacterGenerator characterGenerator = new CharacterGenerator();
        List<Character> sequence1 = characterGenerator.generateCharacters()
            .take(3)
            .collectList()
            .block();
        List<Character> sequence2 = characterGenerator.generateCharacters()
            .take(2)
            .collectList()
            .block();

        CharacterCreator characterCreator = new CharacterCreator();
        Thread producerThread1 = new Thread(
            () -> characterCreator.consumer.accept(sequence1));
        Thread producerThread2 = new Thread(
            () -> characterCreator.consumer.accept(sequence2));
        List<Character> consolidated = new ArrayList<>();
        characterCreator.createCharacterSequence().subscribe(consolidated::add);

        producerThread1.start();
        producerThread2.start();
        producerThread1.join();
        producerThread2.join();

        assertThat(consolidated).containsExactlyInAnyOrder('a', 'b', 'c', 'a', 'b');
    }
}
