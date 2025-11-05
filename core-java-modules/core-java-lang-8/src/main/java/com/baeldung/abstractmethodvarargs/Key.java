package com.example.items;

/** A Key item that needs a KeyContext to operate. */
public class Key extends Item<KeyContext> {
    private final String name;

    public Key(String name) {
        this.name = name;
    }

    @Override
    public void use(KeyContext ctx) {
        System.out.println("Using key '" + name + "' on door: " + ctx.getDoorId());
        System.out.println("Doors remaining in queue: " + ctx.getDoorsQueue().size());
    }
}
