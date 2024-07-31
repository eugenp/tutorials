package com.baeldung.enums.mapping.order;

public enum OrderStatus {
    PENDING, APPROVED, PACKED, DELIVERED;

    public CmsOrderStatus toCmsOrderStatus() {
        return CmsOrderStatus.valueOf(this.name());
    }

    public CmsOrderStatus toCmsOrderStatusOrdinal() {
        return CmsOrderStatus.values()[this.ordinal()];
    }
}
