package shallow;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShallowCopyTest {

    @Test
    public void deep_copy_should_create_different_object() throws CloneNotSupportedException {
        // Given
        Human aHuman = new Human("human1");
        Cat aCat1 = new Cat(4, true, aHuman);

        // When
        Cat aCat2 = (Cat) aCat1.clone();
        aCat2.getOwner().setName("human2");

        // Then
        assertThat(aCat2.getOwner()).isSameAs(aCat1.getOwner());

    }
}