package com.baeldung.collections.iterable;

class Element {

    private Element prev;
    private Element next;
    private Product product;

    public Element(Product product) {
        this.product = product;
    }

    public Element getPrev() {
        return prev;
    }

    public void setPrev(Element prev) {
        this.prev = prev;
    }

    public Element getNext() {
        return next;
    }

    public void setNext(Element next) {
        this.next = next;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
