package com.ssobchenko.model;

import com.ssobchenko.model.device.Device;
import com.ssobchenko.model.device.InvoiceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class Invoice {
    private final Customer customer;
    private final InvoiceType type;
    private final LocalDateTime created;
    private final Map<String, List<? extends Device>> listOfProducts;
    private final int fullInvoicePrice;

    private Invoice(Builder builder) {
        this.customer = builder.customer;
        this.type = builder.type;
        this.created = builder.created;
        this.listOfProducts = builder.listOfProducts;
        this.fullInvoicePrice = builder.fullInvoicePrice;
    }

    public static class Builder {
        private final int limit;
        private final Customer customer;
        private InvoiceType type = InvoiceType.RETAIL;
        private final LocalDateTime created;
        private final Map<String, List<? extends Device>> listOfProducts;
        private int fullInvoicePrice;

        public Builder(Customer customer, LocalDateTime created, Map<String, List<? extends Device>> listOfProducts, int limit) {
            this.customer = customer;
            this.created = created;
            this.listOfProducts = listOfProducts;
            this.limit = limit;
        }

        private int getSumOfProducts() {
            return listOfProducts.values().stream()
                    .flatMap(Collection::stream)
                    .mapToInt(Device::getPrice)
                    .sum();
        }

        public Invoice build() {
            if (customer == null || created == null || listOfProducts == null) {
                throw new IllegalArgumentException("Customer, date of creation or list of products can't be null");
            }
            if (listOfProducts.isEmpty()) {
                throw new IllegalArgumentException("List of products can't be empty");
            }
            if ((fullInvoicePrice = getSumOfProducts()) > limit) {
                type = InvoiceType.WHOLESALE;
            }
            if (customer.getAge() < 18) {
                type = InvoiceType.LOW_AGE;
            }
            return new Invoice(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<? extends Device>> entry : listOfProducts.entrySet()) {
            sb.append(entry.getKey()).append(':').append("\n");
            for (Device device : entry.getValue()) {
                sb.append(device).append("\n");
            }
        }
        return "[" + customer + "]" + "\n" +
                "[Invoice {type = '" + type + '\'' +
                ", created = " + created +
                ",\n" + "listOfProducts = " + "\n" + sb +
                "}]";
    }
}