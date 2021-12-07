package com.baeldung.patterns.hexagonal.architecture.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TradeOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MINUTES_AVAILABLE = 1;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Stock stock;

    private LocalDateTime date = LocalDateTime.now();
    private TradeStatus status = TradeStatus.CREATED;
    private TradeType type;
    private Integer amount;

    private Double price;

    TradeOrder() {
    }

    public TradeOrder(TradeType type, Integer amount, Stock stock, Double price) {
        this.type = type;
        this.amount = amount;
        this.stock = stock;
        this.price = price;
    }

    public boolean isExpired() {
        // here we save processing if we already know this order has expired
        if (status == TradeStatus.EXPIRED)
            return true;

        boolean expired = LocalDateTime.now()
            .isAfter(date.plusMinutes(MINUTES_AVAILABLE));

        if (expired)
            status = TradeStatus.EXPIRED;

        return expired;
    }

    public void fulfill() {
        this.status = TradeStatus.FULFILLED;
    }

    public boolean isFulfillable() {
        return isValid() && status == TradeStatus.CREATED && !isExpired();
    }

    public boolean isValid() {
        return type != null && amount != null && stock != null && date != null && price != null;
    }

    public void validate() {
        if (!isValid()) {
            throw new IllegalStateException("invalid order");
        }
    }

    public boolean matches(TradeOrder other) {
        if (!isFulfillable() || other == null || !other.isFulfillable())
            return false;

        if (type.equals(other.getType()))
            return false;

        if (!amount.equals(other.amount))
            return false;

        if (!stock.equals(other.stock))
            return false;

        if (!price.equals(other.price))
            return false;

        return true;
    }

    public TradeStatus cancel() {
        TradeStatus previousStatus = getStatus();
        if (previousStatus != TradeStatus.CREATED) {
            throw new IllegalStateException("cannot cancel an order with status " + previousStatus);
        }

        this.status = TradeStatus.CANCELED;
        return previousStatus;
    }

    public TradeOrder reverseType() {
        if (!isValid()) {
            throw new IllegalArgumentException("invalid order");
        }

        this.id = null;
        this.date = null;
        this.status = TradeStatus.CREATED;
        if (this.type == TradeType.BUY) {
            this.type = TradeType.SELL;
        } else {
            this.type = TradeType.BUY;
        }
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((stock == null) ? 0 : stock.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TradeOrder other = (TradeOrder) obj;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (stock == null) {
            if (other.stock != null)
                return false;
        } else if (!stock.equals(other.stock))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (status != other.status)
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Stock getStock() {
        return stock;
    }

    public TradeType getType() {
        return type;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }
}
