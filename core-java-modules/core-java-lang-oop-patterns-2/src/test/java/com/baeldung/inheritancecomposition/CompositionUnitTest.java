package com.baeldung.inheritancecomposition;

import com.baeldung.inheritancecomposition.model.*;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CompositionUnitTest {
    
    
    @Test
    public void givenComputerInstance_whenExtractedEachField_thenThreeAssertions() {
        Computer computer = new Computer(new StandardProcessor("Intel I3"), new StandardMemory("Kingston", "1TB"));
        computer.setSoundCard(new StandardSoundCard("Generic Sound Card"));
        assertThat(computer.getProcessor()).isInstanceOf(Processor.class);
        assertThat(computer.getMemory()).isInstanceOf(Memory.class);
        assertThat(computer.getSoundCard()).isInstanceOf(Optional.class);
    }
}
