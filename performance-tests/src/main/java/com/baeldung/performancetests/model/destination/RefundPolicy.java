package com.baeldung.performancetests.model.destination;

import com.google.common.base.Objects;
import com.googlecode.jmapper.annotations.JGlobalMap;
import com.googlecode.jmapper.annotations.JMapAccessor;

import java.util.List;

@JGlobalMap
public class RefundPolicy {
    @JMapAccessor(get = "isRefundable", set = "setRefundable")
    private boolean isRefundable;
    private int refundTimeInDays;

    public RefundPolicy() {
    }

    public boolean isRefundable() {
        return isRefundable;
    }

    public void setRefundable(boolean refundable) {
        isRefundable = refundable;
    }

    public int getRefundTimeInDays() {
        return refundTimeInDays;
    }

    public void setRefundTimeInDays(int refundTimeInDays) {
        this.refundTimeInDays = refundTimeInDays;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public RefundPolicy(boolean isRefundable, int refundTimeInDays, List<String> notes) {

        this.isRefundable = isRefundable;
        this.refundTimeInDays = refundTimeInDays;
        this.notes = notes;
    }

    private List<String> notes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() == com.baeldung.performancetests.model.source.RefundPolicy.class) {
            com.baeldung.performancetests.model.source.RefundPolicy that = (com.baeldung.performancetests.model.source.RefundPolicy) o;
            return isRefundable == that.isRefundable() &&
                    refundTimeInDays == that.getRefundTimeInDays() &&
                    Objects.equal(notes, that.getNotes());
        }
        if (o.getClass() != getClass()) return false;
        RefundPolicy that = (RefundPolicy) o;
        return isRefundable == that.isRefundable &&
                refundTimeInDays == that.refundTimeInDays &&
                Objects.equal(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isRefundable, refundTimeInDays, notes);
    }
}
