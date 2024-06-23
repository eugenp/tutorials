import static org.junit.Assert.assertNotSame;

import org.example.DeepNotebook;
import org.junit.Test;

public class NotebookDeepTest {
    @Test
    public void testDeepCopy() {
        DeepNotebook original = new DeepNotebook();
        original.addRecipe("Chocolate Chip Cookies", "Easy to bake.");

        DeepNotebook copy = new DeepNotebook(original);

        assertNotSame(original.recipes.get(0), copy.recipes.get(0));  // Different Recipe objects
    }
}
