package com.baeldung.jhipster.quotes.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the Quote entity. This class is used in QuoteResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /quotes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuoteCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter symbol;

    private BigDecimalFilter price;

    private ZonedDateTimeFilter lastTrade;

    public QuoteCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSymbol() {
        return symbol;
    }

    public void setSymbol(StringFilter symbol) {
        this.symbol = symbol;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public ZonedDateTimeFilter getLastTrade() {
        return lastTrade;
    }

    public void setLastTrade(ZonedDateTimeFilter lastTrade) {
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
        final QuoteCriteria that = (QuoteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(symbol, that.symbol) &&
            Objects.equals(price, that.price) &&
            Objects.equals(lastTrade, that.lastTrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        symbol,
        price,
        lastTrade
        );
    }

    @Override
    public String toString() {
        return "QuoteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (symbol != null ? "symbol=" + symbol + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (lastTrade != null ? "lastTrade=" + lastTrade + ", " : "") +
            "}";
    }

}
