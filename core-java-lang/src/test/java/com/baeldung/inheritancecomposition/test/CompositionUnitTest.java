package com.baeldung.inheritancecomposition.test;

import com.baeldung.inheritancecomposition.model.Computer;
import com.baeldung.inheritancecomposition.model.Memory;
import com.baeldung.inheritancecomposition.model.Processor;
import com.baeldung.inheritancecomposition.model.StandardMemory;
import com.baeldung.inheritancecomposition.model.StandardProcessor;
import com.baeldung.inheritancecomposition.model.StandardSoundCard;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

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
