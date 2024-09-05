package deep;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DeepCopyTest {

    @Test
    public void deep_copy_should_create_different_object() throws CloneNotSupportedException {
        // Given
        Human aHuman = new Human("human1");
        Cat originalCat = new Cat(4, true, aHuman);

        // When
        Cat copyCat = (Cat) originalCat.clone();
        copyCat.getOwner().setName("human2");

        // Then
        assertThat(copyCat.getOwner()).isNotSameAs(originalCat.getOwner());

    }
}