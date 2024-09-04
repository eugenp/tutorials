package shallow;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShallowCopyTest {

    @Test
    public void shallow_copy_should_copy_reference_of_same_object() throws CloneNotSupportedException {
        // Given
        Human aHuman = new Human("human1");
        Cat originalCat = new Cat(4, true, aHuman);

        // When
        Cat copyCat = (Cat) originalCat.clone();
        copyCat.getOwner().setName("human2");
        copyCat.setAge(5);

        // Then
        assertThat(copyCat.getOwner()).isSameAs(originalCat.getOwner());
        assertThat(originalCat.getAge()).isEqualTo(4);

    }
}