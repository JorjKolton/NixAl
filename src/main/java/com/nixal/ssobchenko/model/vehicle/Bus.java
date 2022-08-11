package com.nixal.ssobchenko.model.vehicle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Bus extends Vehicle {
    private int numberOfSeats;
    private BusManufacturer busManufacturer;

    private Bus(Builder builder) {
        super(builder.model, builder.price, VehicleType.BUS);
        this.numberOfSeats = builder.numberOfSeats;
        this.busManufacturer = builder.busManufacturer;
        this.count = builder.count;
    }

    public static class Builder {
        private final int model;
        private final BusManufacturer busManufacturer;
        private final BigDecimal price;
        private int numberOfSeats;
        private int count;

        public Builder(int model, BusManufacturer busManufacturer, BigDecimal price) {
            this.model = model;
            this.busManufacturer = busManufacturer;
            this.price = price;
        }

        public Builder setNumberOfSeats(int numberOfSeats) {
            this.numberOfSeats = numberOfSeats;
            return this;
        }

        public Builder setCount(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("Count can't be less than zero");
            }
            this.count = count;
            return this;
        }

        public Bus build() {
            if (busManufacturer == null || price == null) {
                throw new IllegalArgumentException("BusManufacturer or price can't be null");
            }
            return new Bus(this);
        }
    }

    public static class NumberOfSeatsComparator implements Comparator<Bus> {

        @Override
        public int compare(Bus o1, Bus o2) {
            return Integer.compare(o1.getNumberOfSeats(), o2.getNumberOfSeats());
        }
    }

    @Override
    public String toString() {
        return "Bus{" + busManufacturer + " with " +
                numberOfSeats + " seats" +
                ", id ='" + id + '\'' +
                ", model ='" + model + '\'' +
                ", price = " + price + currency +
                '}';
    }
}