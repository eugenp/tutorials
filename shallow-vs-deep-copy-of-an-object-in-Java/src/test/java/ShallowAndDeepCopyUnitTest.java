import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShallowAndDeepCopyUnitTest {

    // Movie class definition
    public static class Movie implements Cloneable, Serializable {
        private static final long serialVersionUID = 1L;
        private String title;
        private List<String> actors;

        public Movie(String title, List<String> actors) {
            this.title = title;
            this.actors = actors;
        }

        // Shallow copy method
        public Movie shallowCopy() {
            try {
                return (Movie) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }

        // Deep copy method
        public Movie deepCopy() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos);
                out.writeObject(this);

                ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                ObjectInputStream in = new ObjectInputStream(bis);
                return (Movie) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    // Test methods
    @Test
    public void testShallowCopy() {
        // Create and modify the original movie
        Movie originalMovie = new Movie("Inception", new ArrayList<>());
        originalMovie.actors.add("Leonardo DiCaprio");

        // Create a shallow copy of the movie
        Movie copiedMovie = originalMovie.shallowCopy();

        // Modify the copied movie
        copiedMovie.actors.add("Tom Hardy");

        // Check if the original movie is affected
        assertEquals("Original Movie should be modified", 2, originalMovie.actors.size());
        assertEquals("Original Movie title should remain unchanged", "Inception", originalMovie.title);

        // Check if the copied movie reflects the changes
        assertEquals("Copied Movie should have additional actor", 2, copiedMovie.actors.size());
        assertEquals("Copied Movie title should be the same", "Inception", copiedMovie.title);
    }

    @Test
    public void testDeepCopy() {
        // Create and modify the original movie
        Movie originalMovie = new Movie("Inception", new ArrayList<>());
        originalMovie.actors.add("Leonardo DiCaprio");

        // Create a deep copy of the movie
        Movie copiedMovie = originalMovie.deepCopy();

        // Modify the copied movie
        copiedMovie.actors.add("Tom Hardy");

        // Check if the original movie remains unchanged
        assertEquals("Original Movie should not be modified", 1, originalMovie.actors.size());
        assertEquals("Original Movie title should remain unchanged", "Inception", originalMovie.title);

        // Check if the copied movie reflects the changes
        assertEquals("Copied Movie should have additional actor", 2, copiedMovie.actors.size());
        assertEquals("Copied Movie title should be the same", "Inception", copiedMovie.title);
    }
}
