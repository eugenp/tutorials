package com.baeldung.collections.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ShoppingCart implements Iterable<Product> {

    private Element firstElement;
    private Element lastElement;

    public void add(Product product) {
        Element newElement = new Element(product);
        connectElements(newElement);
    }

    private void connectElements(Element newElement) {
        if (isEmpty()) {
            addFirstElement(newElement);
            return;
        }

        addLastElement(newElement);
    }

    private boolean isEmpty() {
        return firstElement == null;
    }

    private void addFirstElement(Element newElement) {
        firstElement = newElement;
        lastElement = newElement;
    }

    private void addLastElement(Element newElement) {
        newElement.setPrev(lastElement);
        lastElement.setNext(newElement);
        lastElement = newElement;
    }

    @Override
    public Iterator<Product> iterator() {
        return new ShoppingCartIterator();
    }

    class ShoppingCartIterator implements Iterator<Product> {
        private Element currentElement;

        public ShoppingCartIterator() {
            currentElement = ShoppingCart.this.firstElement;
        }

        @Override
        public boolean hasNext() {
            return currentElement != null;
        }

        @Override
        public Product next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return getNext();
        }

        private Product getNext() {
            Element element = currentElement;
            currentElement = currentElement.getNext();
            return element.getProduct();
        }
    }
}
