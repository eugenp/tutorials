package com.baeldung.jhipster.quotes.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Quote entity.
 */
public class QuoteDTO implements Serializable {

    private Long id;

    @NotNull
    private String symbol;

    @NotNull
    private BigDecimal price;

    @NotNull
    private ZonedDateTime lastTrade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ZonedDateTime getLastTrade() {
        return lastTrade;
    }

    public void setLastTrade(ZonedDateTime lastTrade) {
        this.lastTrade = lastTrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuoteDTO quoteDTO = (QuoteDTO) o;
        if (quoteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quoteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
            "id=" + getId() +
            ", symbol='" + getSymbol() + "'" +
            ", price=" + getPrice() +
            ", lastTrade='" + getLastTrade() + "'" +
            "}";
    }
}
