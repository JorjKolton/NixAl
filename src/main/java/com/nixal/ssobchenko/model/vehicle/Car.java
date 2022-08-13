package com.nixal.ssobchenko.model.vehicle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Car extends Vehicle {
    private CarBodyType bodyType;
    private CarManufacturer carManufacturer;

    private Car(Builder builder) {
        super(builder.model, builder.price, VehicleType.CAR);
        this.bodyType = builder.bodyType;
        this.carManufacturer = builder.carManufacturer;
    }

    public static class Builder {
        private final int model;
        private final CarManufacturer carManufacturer;
        private final BigDecimal price;
        private CarBodyType bodyType;
        private int count;

        public Builder(int model, CarManufacturer carManufacturer, BigDecimal price) {
            this.model = model;
            this.carManufacturer = carManufacturer;
            this.price = price;
        }

        public Builder setCarBodyType(CarBodyType bodyType) {
            if (bodyType == null || bodyType.name().length() > 20) {
                throw new IllegalArgumentException("BodyType can't be null or longer than 20 characters");
            }
            this.bodyType = bodyType;
            return this;
        }

        public Builder setCount(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("Count can't be less than zero");
            }
            this.count = count;
            return this;
        }

        public Car build() {
            if (carManufacturer == null || price == null) {
                throw new IllegalArgumentException("CarManufacturer or price can't be null");
            }
            return new Car(this);
        }
    }

    @Override
    public String toString() {
        return "Car {" + bodyType + " from " +
                carManufacturer + ", id = '" + id + '\'' +
                ", model = '" + model + '\'' +
                ", price = " + price + currency + '}';
    }
}