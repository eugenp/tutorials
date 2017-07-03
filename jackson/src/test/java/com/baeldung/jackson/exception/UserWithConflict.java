package com.baeldung.jackson.exception;

class UserWithConflict {
    private int id;
    private String name;
    private boolean checked;

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
