package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.statemachine.config.ForkJoinStateMachineConfiguration;
import com.baeldung.spring.statemachine.config.HierarchicalStateMachineConfiguration;
import com.baeldung.spring.statemachine.config.JunctionStateMachineConfiguration;
import com.baeldung.spring.statemachine.config.SimpleEnumStateMachineConfiguration;
import com.baeldung.spring.statemachine.config.SimpleStateMachineConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SimpleStateMachineConfiguration.class, SimpleEnumStateMachineConfiguration.class,
		JunctionStateMachineConfiguration.class, HierarchicalStateMachineConfiguration.class,
		ForkJoinStateMachineConfiguration.class })
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
