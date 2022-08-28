package com.nixal.ssobchenko.model.vehicle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Bus extends Vehicle {
    private int numberOfSeats;
    private BusManufacturer busManufacturer;

    private Bus(Builder builder) {
        super(builder.id, builder.model, builder.price, VehicleType.BUS, builder.created);
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
        private String id;
        private LocalDateTime created;

        public Builder(int model, BusManufacturer busManufacturer, BigDecimal price) {
            this.model = model;
            this.busManufacturer = busManufacturer;
            this.price = price;
            id = UUID.randomUUID().toString();
            created = LocalDateTime.now();
        }

        public Bus.Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Bus.Builder setCreated(LocalDateTime created) {
            this.created = created;
            return this;
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