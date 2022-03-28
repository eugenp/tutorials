package com.baeldung.deepcopy;

import java.util.ArrayList;
import java.util.List;

class LockBox {
    private List<Integer> combination;
    private boolean isLocked;

    public LockBox(List<Integer> combination) {
        this.combination = combination;
        this.isLocked = false;
    }

    public LockBox(LockBox lockBox) {
        this(new ArrayList<>(lockBox.getCombination()));
    }

    public List<Integer> getCombination() {
        return this.combination;
    }

    public LockBox getShallowCopy() {
        LockBox newLockBox = new LockBox(this.combination);
        return newLockBox;
    }

    public LockBox getDeepCopy() {
        List<Integer> combination = new ArrayList<>(this.combination);
        LockBox newLockBox = new LockBox(combination);
        return newLockBox;
    }
}
