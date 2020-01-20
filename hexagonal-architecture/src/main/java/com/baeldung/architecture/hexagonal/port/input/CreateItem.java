package com.baeldung.architecture.hexagonal.port.input;

import java.util.*;

public interface CreateItem
{
    UUID createItem(String name);
}
