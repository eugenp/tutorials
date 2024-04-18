package com.baeldung.mapstruct.enumtostring.model;

/**
 * The type Order.
 */
public class Order {
    private Long id;
    private String summary;
    private OrderType orderType;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets order type.
     *
     * @return the order type
     */
    public OrderType getOrderType() {
        return orderType;
    }

    /**
     * Sets order type.
     *
     * @param orderType the order type
     */
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    /**
     * Gets summary.
     *
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets summary.
     *
     * @param summary the summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
