import com.baeldung.spymethods.CatTantrum;
import com.baeldung.spymethods.CatTantrum.CatAction;
import com.baeldung.spymethods.CatTantrum.HumanReaction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CatTantrumUnitTest {

    @Test
    public void givenMockMethodHumanReactions_whenCatActionBite_thenHumanReactionsBiteBack(){
        //Given
        CatTantrum catTantrum = new CatTantrum();
        CatTantrum catTantrum1 = Mockito.spy(catTantrum);
        Mockito.doReturn(HumanReaction.BITE_BACK).when(catTantrum1).biteCatBack();

        //When
        HumanReaction humanReaction1 = catTantrum1.whatIsHumanReaction(CatAction.BITE);

        //Then
        assertEquals(humanReaction1, HumanReaction.BITE_BACK);
    }

}
