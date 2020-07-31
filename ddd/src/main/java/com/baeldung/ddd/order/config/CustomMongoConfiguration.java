package com.baeldung.ddd.order.config;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.math.BigDecimal;
import java.util.Collections;

@Configuration
public class CustomMongoConfiguration {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Collections.singletonList(DocumentToMoneyConverter.INSTANCE));
    }

    @ReadingConverter
    enum DocumentToMoneyConverter implements Converter<Document, Money> {

        INSTANCE;

        @Override
        public Money convert(Document source) {
            Document money = source.get("money", Document.class);

            return Money.of(getCurrency(money), getAmount(money));
        }

        private CurrencyUnit getCurrency(Document money) {
            Document currency = money.get("currency", Document.class);
            String currencyCode = currency.getString("code");
            return CurrencyUnit.of(currencyCode);
        }

        private BigDecimal getAmount(Document money) {
            String amount = money.getString("amount");
            return BigDecimal.valueOf(Double.parseDouble(amount));
        }
    }
}
