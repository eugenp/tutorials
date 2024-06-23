import static org.junit.Assert.assertEquals;

import org.example.ShallowNotebook;
import org.junit.Test;

public class NotebookShallowTest {
    @Test
    public void testShallowCopy() {
        ShallowNotebook original = new ShallowNotebook();
        original.addRecipe("Pasta Carbonara", "Classic Italian pasta.");

        ShallowNotebook copy = new ShallowNotebook(original);

        assertEquals(original.recipes.get(0), copy.recipes.get(0));  // Same Recipe object
    }
}
