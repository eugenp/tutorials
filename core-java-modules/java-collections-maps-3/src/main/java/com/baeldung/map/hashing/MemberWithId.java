package com.baeldung.map.hashing;

public class MemberWithId extends Member {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberWithId that = (MemberWithId) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
