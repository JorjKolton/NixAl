package com.nixal.ssobchenko.model.vehicle;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.UUID;

@Getter
@Setter
public abstract class Vehicle {
    protected final String id;
    protected String model;
    protected BigDecimal price;

    protected Vehicle(String model, BigDecimal price) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
    }

    public static class IdComparator implements Comparator <Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }

    public static class PriceComparator implements Comparator <Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    }

    public static class ModelComparator implements Comparator <Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getModel().compareTo(o2.getModel());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (!id.equals(vehicle.id)) return false;
        if (!model.equals(vehicle.model)) return false;
        return price.equals(vehicle.price);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}