package fiandlambdas;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Foo_3 {

    String addWithFunction(Function<String, String> f);

    void addWithConsumer(Consumer<Integer> f);

}
