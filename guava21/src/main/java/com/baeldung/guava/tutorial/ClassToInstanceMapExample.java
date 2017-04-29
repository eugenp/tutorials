package com.baeldung.guava.tutorial;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

public class ClassToInstanceMapExample {
    public static void main(String[] args) {
        ClassToInstanceMap<Action> map = MutableClassToInstanceMap.create();

        map.put(Save.class, new Save());
        map.put(Open.class, new Open());
        map.putInstance(Delete.class, new Delete());

        Action saveAction = map.get(Save.class);
        Open openAction = (Open) map.get(Open.class);
        Delete deleteAction = map.getInstance(Delete.class);
    }
}

abstract class Action {
}

class Save extends Action {
}

class Open extends Action {
}

class Delete extends Action {
}