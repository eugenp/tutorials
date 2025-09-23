package com.example.items;

/**
 * Generic abstract base class for items.
 *
 * @param <C> the type of the context object that contains parameters
 *            needed to "use" the item.
 */
public abstract class Item<C> {
    /**
     * Use the item with a typed context object.
     * Subclasses will specify C and implement this method.
     */
    public abstract void use(C context);
}
