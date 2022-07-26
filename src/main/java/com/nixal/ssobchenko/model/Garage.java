package com.nixal.ssobchenko.model;

import com.nixal.ssobchenko.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Garage<E extends Vehicle> implements Iterable<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

    public void addFirst(E vehicle, int restyle) {
        final Node<E> next = first;
        final Node<E> newNode = new Node<>(null, vehicle, next, restyle);
        first = newNode;
        if (next == null) {
            last = newNode;
        } else {
            next.prevElement = newNode;
        }
        size++;
    }

    public void add(E vehicle, int restyle) {
        final Node<E> previous = last;
        final Node<E> newNode = new Node<>(previous, vehicle, null, restyle);
        last = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.nextElement = newNode;
        }
        size++;
    }

    public E findVehicleByRestylingNumber(int number) {
        return findNodeByRestylingNumber(number).getCurrentElement();
    }

    public void replaceVehicleByRestylingNumber(int number, E vehicle) {
        Node<E> found = findNodeByRestylingNumber(number);
        found.setCurrentElement(vehicle);
        found.setDateOfAddition(new Date());
    }

    public void removeVehicleByRestylingNumber(int number) {
        Node<E> current = findNodeByRestylingNumber(number);
        Node<E> next = current.getNextElement();
        Node<E> previous = current.getPrevElement();

        if (previous != null) {
            previous.setNextElement(next);
        } else {
            first = next;
        }

        if (next != null) {
            next.setPrevElement(previous);
        } else {
            last = previous;
        }

        current.setPrevElement(null);
        current.setNextElement(null);
        current.setCurrentElement(null);

        size--;
    }

    public void showFirstRestylingData() {
        switch (size) {
            case 0 -> System.out.println("The garage is empty");
            case 1 -> System.out.println("First restyling data is - " + first.dateOfAddition);
            default -> {
                Date date = first.getDateOfAddition();
                Node<E> next = first.getNextElement();
                for (int i = 1; i < size; i++) {
                    if (date.after(next.getDateOfAddition())) {
                        date = next.getDateOfAddition();
                    }
                    next = next.getNextElement();
                }
                System.out.println("First restyling data is - " + date);
            }
        }
    }

    public void showLastRestylingData() {
        switch (size) {
            case 0 -> System.out.println("The garage is empty");
            case 1 -> System.out.println("Last restyling data is - " + first.dateOfAddition);
            default -> {
                Date date = first.getDateOfAddition();
                Node<E> next = first.getNextElement();
                for (int i = 1; i < size; i++) {
                    if (date.before(next.getDateOfAddition())) {
                        date = next.getDateOfAddition();
                    }
                    next = next.getNextElement();
                }
                System.out.println("Last restyling data is - " + date);
            }
        }
    }

    private Node<E> findNodeByRestylingNumber(int number) {
        Node<E> result = first;
        for (int i = 0; i < size; i++) {
            if (result.getRestylingNumber() == number) {
                return result;
            }
            result = result.nextElement;
        }
        throw new NoSuchElementException("There isn't vehicle with such restyling number");
    }

    public int numberOfRestyling() {
        return size;
    }

    @Getter
    @Setter
    private static class Node<E> {
        E currentElement;
        Node<E> nextElement;
        Node<E> prevElement;
        private int restylingNumber;
        private Date dateOfAddition;

        Node(Node<E> prev, E element, Node<E> next, int restylingNumber) {
            this.currentElement = element;
            this.nextElement = next;
            this.prevElement = prev;
            if (element == null) {
                this.restylingNumber = 0;
            } else {
                this.restylingNumber = restylingNumber;
                dateOfAddition = new Date();
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new GarageIterator();
    }

    private class GarageIterator implements Iterator<E> {
        private Node<E> next = first;
        private int nextIndex;

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Garage is empty");
            }
            Node<E> lastReturned = next;
            next = next.nextElement;
            nextIndex++;
            return lastReturned.currentElement;
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Garage for ").append(size).append(" vehicles:").append("\n");
        Node<E> current = first;
        for (int i = 0; i < size; i++) {
            builder.append(String.format("Vehicle %s price %.2f%n",
                    current.getCurrentElement().getModel(),
                    current.getCurrentElement().getPrice()));
            current = current.nextElement;
        }
        return builder.toString();
    }
}