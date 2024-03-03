package com.baeldung.copy;

public class JavaCopyApplication {

    public static void main(String[] args) throws CloneNotSupportedException {
        shallowCopy();
        deepCopy();
    }

    private static void deepCopy() {
        Item item = new Item("mac", 15);
        Shop s1 = new Shop("shop-1", item);
        Shop s2 = new Shop(s1);
    }

    private static void shallowCopy() throws CloneNotSupportedException {
        Item item = new Item("mac", 15);
        Shop s1 = new Shop("shop-1", item);
        Shop s2 = (Shop) s1.clone();
    }

    public static class Item {

        private String name;
        private double price;

        public Item(Item that) {
            this(that.name, that.price);
        }

        public Item(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public static class Shop implements Cloneable {

        private String name;
        private Item item;

        public Shop(Shop that) {
            this(that.name, new Item(that.item));
        }

        public Shop(String name, Item item) {
            this.name = name;
            this.item = item;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
