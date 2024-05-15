package com.baeldung.exceptions;

public class UserWithConflict {
    public int id;
    public String name;
    boolean checked;

    public UserWithConflict() {
        super();
    }

    public UserWithConflict(final int id, final String name, final boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public boolean getChecked() {
        return checked;
    }

    public boolean isChecked() {
        return checked;
    }
}
