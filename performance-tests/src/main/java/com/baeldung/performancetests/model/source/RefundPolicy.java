package com.baeldung.performancetests.model.source;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.googlecode.jmapper.annotations.JMapAccessor;

import java.util.List;

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
}
