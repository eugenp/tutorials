package com.example.items;

import java.util.Queue;

/**
 * Context data for Key items.
 * Immutable fields with getters give clear intent and type safety.
 */
public final class KeyContext {
    private final String doorId;
    private final Queue<String> doorsQueue;

    public KeyContext(String doorId, Queue<String> doorsQueue) {
        this.doorId = doorId;
        this.doorsQueue = doorsQueue;
    }

    public String getDoorId() {
        return doorId;
    }

    public Queue<String> getDoorsQueue() {
        return doorsQueue;
    }
}
