package com.nixal.ssobchenko.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Vehicle {
    protected final String id;
    protected int model;
    protected BigDecimal price;
    protected VehicleType type;
    protected List<String> details;
    protected String currency;
    protected int count;
    protected LocalDateTime created;
    protected Engine engine;

    protected Vehicle(int model, BigDecimal price, VehicleType type) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
        this.type = type;
        currency = "₴";
        created = LocalDateTime.now();
        engine = new Engine();
    }

    public static class IdComparator implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }

    public static class PriceComparator implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    }

    public static class ModelComparator implements Comparator<Vehicle> {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return Integer.compare(o1.getModel(), o2.getModel());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (model != vehicle.model) return false;
        if (!id.equals(vehicle.id)) return false;
        if (!price.equals(vehicle.price)) return false;
        return type == vehicle.type;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + model;
        result = 31 * result + price.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}