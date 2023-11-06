import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {

    @Test
    public void givenaNewInstanceofBox_whenTheCopiedBoxisCreatedFromTheOriginalBox_thenItIsShallowCopy() {
        Box originalBox = new Box(10);

        Box copiedBox = originalBox;

        copiedBox.setOranges(20);

        int originalOranges = originalBox.getOranges();
        assertEquals(20, originalOranges);
    }

    @Test
    public void givenaNewInstanceofBox_whenTheCopiedBoxisCreatedUsingMemberVariablesInTheOriginal_thenItIsDeepCopy() {
        Box originalBox = new Box(10);

        Box copiedBox = new Box(originalBox.getOranges());

        copiedBox.setOranges(20);

        int originalOranges = originalBox.getOranges();
        assertEquals(10, originalOranges);
    }
}
