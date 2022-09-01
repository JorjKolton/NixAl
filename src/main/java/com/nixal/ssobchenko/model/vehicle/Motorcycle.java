package com.nixal.ssobchenko.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Motorcycle extends Vehicle {
    private MotorcycleBodyType bodyType;
    private MotorcycleManufacturer motorcycleManufacturer;

    private Motorcycle(Builder builder) {
        super(builder.id, builder.model, builder.price, VehicleType.MOTORCYCLE, builder.created);
        this.bodyType = builder.bodyType;
        this.motorcycleManufacturer = builder.motorcycleManufacturer;
    }

    public static class Builder {
        private final int model;
        private final MotorcycleManufacturer motorcycleManufacturer;
        private final BigDecimal price;
        private MotorcycleBodyType bodyType;
        private int count;
        private String id;
        private LocalDateTime created;

        public Builder(int model, MotorcycleManufacturer motorcycleManufacturer, BigDecimal price) {
            this.model = model;
            this.motorcycleManufacturer = motorcycleManufacturer;
            this.price = price;
            id = UUID.randomUUID().toString();
            created = LocalDateTime.now();
        }

        public Builder setMotorcycleBodyType(MotorcycleBodyType bodyType) {
            if (bodyType == null || bodyType.name().length() > 20) {
                throw new IllegalArgumentException("BodyType can't be null or longer than 20 characters");
            }
            this.bodyType = bodyType;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCreated(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Builder setCount(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("Count can't be less than zero");
            }
            this.count = count;
            return this;
        }

        public Motorcycle build() {
            if (motorcycleManufacturer == null || price == null) {
                throw new IllegalArgumentException("CarManufacturer or price can't be null");
            }
            return new Motorcycle(this);
        }
    }

    @Override
    public String toString() {
        return "Motorcycle {" + bodyType + " from " +
                motorcycleManufacturer + ", id = '" + id + '\'' +
                ", model = '" + model + '\'' +
                ", price = " + price + currency + '}';
    }
}