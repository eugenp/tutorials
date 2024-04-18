package com.baeldung.mapstruct.enumtostring.model;

/**
 * The type external order.
 */
public class ExternalOrder {
    private Long id;
    private String summary;
    private String orderType;

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
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets order type.
     *
     * @param orderType the order type
     */
    public void setOrderType(String orderType) {
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
