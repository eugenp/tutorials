import com.baeldung.mockito.spymethods.Cat;
import com.baeldung.mockito.spymethods.Human;
import com.baeldung.mockito.spymethods.Cat.Action;
import com.baeldung.mockito.spymethods.Human.HumanReaction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CatUnitTest {

    @Test
    public void givenRealHumanClass_whenCatActionBite_thenHumanReactionsBiteBack(){
        //Given
        Human Sophie = new Human();
        Cat felix = new Cat(Sophie);

        //When
        HumanReaction humanReaction = felix.whatIsHumanReaction(Action.BITE);

        //Then
        assertEquals(humanReaction, HumanReaction.BITE_BACK);

    }

    @Test
    public void givenMockHumanClass_whenCatActionBite_thenHumanReactionsBiteBack(){
        //Given
        Human mockHuman = mock(Human.class);
        Cat felix = new Cat(mockHuman);
        when(mockHuman.biteCatBack()).thenReturn(HumanReaction.BITE_BACK);

        //When
        HumanReaction humanReaction = felix.whatIsHumanReaction(Action.BITE);

        //Then
        assertEquals(humanReaction, HumanReaction.BITE_BACK);

    }

    @Test
    public void givenMockMethodHumanReactions_whenCatActionBite_thenHumanReactionsBiteBack(){
        //Given
        Cat cat = new Cat();
        Cat cat1 = Mockito.spy(cat);
        Mockito.doReturn(HumanReaction.BITE_BACK).when(cat1).whatIsHumanReaction(Action.BITE);

        //When
        HumanReaction humanReaction = cat.whatIsHumanReaction(Action.VOMIT_ON_CARPET);
        HumanReaction humanReaction1 = cat1.whatIsHumanReaction(Action.BITE);

        //Then
        assertEquals(humanReaction1, HumanReaction.BITE_BACK);
        assertEquals(humanReaction, HumanReaction.CLEAN);

    }

}
