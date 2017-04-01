package services;

/**
 * This interface demonstrates how to create a component that is injected
 * into a controller. The interface represents a counter that returns a
 * incremented number each time it is called.
 *
 * The {@link Modules} class binds this interface to the
 * {@link AtomicCounter} implementation.
 */
public interface Counter {
    int nextCount();
}
