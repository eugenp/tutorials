package fiandlambdas;

@FunctionalInterface
public interface Foo_2 extends Foo, Foo_1 {

    @Override
    default void defaultMethod() {
        Foo_1.super.defaultMethod();
    }

}
