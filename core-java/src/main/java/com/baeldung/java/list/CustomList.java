package com.baeldung.java.list;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomList<E> implements List<E> {
    private Object[] internal = {};

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return internal.length;
    }

    @Override
    public boolean isEmpty() {
        return internal.length == 0;
    }

    @Override
    public boolean add(E element) {
        // the first cycle
        // internal = new Object[1];
        // internal[0] = element;
        // return true;

        Object[] temp = new Object[internal.length + 1];
        System.arraycopy(internal, 0, temp, 0, internal.length);
        temp[internal.length] = element;
        internal = temp;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        return (E) internal[index];
    }

    @Override
    public boolean contains(Object object) {
        // return false

        for (Object element : internal) {
            if (object.equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        // the first cycle
        // for (Object element : collection) {
        //     if (element.equals(internal[0])) {
        //         return true;
        //     }
        // }
        // return false;

        for (Object element : collection)
            if (!contains(element)) {
                return false;
            }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        E oldElement = (E) internal[index];
        internal[index] = element;
        return oldElement;
    }

    @Override
    public void clear() {
        internal = new Object[0];
    }

    @Override
    public int indexOf(Object object) {
        // the first cycle
        // if (object.equals(internal[0])) {
        //     return 0;
        // }
        // return -1;

        for (int i = 0; i < internal.length; i++) {
            if (object.equals(internal[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        // the first cycle
        // if (object.equals(internal[0])) {
        //     return 0;
        // }
        // return -1;

        for (int i = internal.length - 1; i >= 0; i--) {
            if (object.equals(internal[i])) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // the first cycle
        // return (List<E>) Arrays.asList(internal);

        Object[] temp = new Object[toIndex - fromIndex];
        System.arraycopy(internal, fromIndex, temp, 0, temp.length);
        return (List<E>) Arrays.asList(temp);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(internal, internal.length);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] array) {
        // the first cycle
        // array[0] = (T) internal[0];
        // return array;

        // the second cycle
        // if (array.length < internal.length) {
        //     return (T[]) Arrays.copyOf(internal, internal.length, array.getClass());
        // }
        // return (T[]) Arrays.copyOf(internal, internal.length, array.getClass());

        if (array.length < internal.length) {
            return (T[]) Arrays.copyOf(internal, internal.length, array.getClass());
        }

        System.arraycopy(internal, 0, array, 0, internal.length);
        if (array.length > internal.length) {
            array[internal.length] = null;
        }
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        // ignored for brevity
        return null;
    }

    private class CustomIterator implements Iterator<E> {
        int index;

        @Override
        public boolean hasNext() {
            // the first cycle
            // return true;

            return index != internal.length;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            // the first cycle
            // return (E) CustomList.this.internal[0];

            E element = (E) CustomList.this.internal[index];
            index++;
            return element;
        }
    }
}
