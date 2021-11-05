package com.baeldung.lambdaserialization;

import java.io.Serializable;

@FunctionalInterface
public interface SerializableRunnable extends Runnable, Serializable {
}
