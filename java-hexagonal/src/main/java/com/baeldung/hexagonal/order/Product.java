package com.baeldung.hexagonal.order;

import org.joda.money.Money;

public class Product {
        private Money price;

        public Product() {
        }

        public Product(Money price) {
                this.price = price;
        }

        public Money getPrice() {
                return price;
        }

        public void setPrice(Money price) {
                this.price = price;
        }
}
