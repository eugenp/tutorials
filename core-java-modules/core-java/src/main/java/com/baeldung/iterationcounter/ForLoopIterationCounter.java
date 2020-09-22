import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

public class IterationCounter {
    private final List<String> colors = Arrays.asList("red", "blue", "yellow", "green");

    private void forEachCounter() {
        int counter = 0;
        for (String color : colors) {
            counter++;
            System.out.println("Value:" + color + ", iteration: " + counter);
        }
    }

    public void listIterator() {
        for (ListIterator<String> color = colors.listIterator(); color.hasNext(); ) {
            int i = color.nextIndex();
            System.out.println("Value:" + color.next() + ", iteration: " + (i + 1));
        }
    }

    private void forEachLambdaAtomicCounter() {
        final AtomicInteger indexHolder = new AtomicInteger();
        colors.forEach(color -> {
            final int index = indexHolder.incrementAndGet();
            System.out.println("Value:" + color + ", iteration: " + index);
        });
    }

    private void forEachLambda() {
        forEachWithFunctionalInterface(colors, (color, i) -> System.out.println("Value:" + color + ", iteration: " + (i + 1)));
    }

    @FunctionalInterface
    public interface loopWithIndex<T> {
        void accept(T t, int i);
    }

    public static <T> void forEachWithFunctionalInterface(Collection<T> collection,
                                                          loopWithIndex<T> consumer) {
        int index = 0;
        for (T object : collection) {
            consumer.accept(object, index++);
        }
    }
}
