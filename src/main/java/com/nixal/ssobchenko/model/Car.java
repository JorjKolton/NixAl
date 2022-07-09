package com.nixal.ssobchenko.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Car extends Vehicle {
    private CarBodyType bodyType;
    private CarManufacturer carManufacturer;

    public Car(String model, CarManufacturer carManufacturer, BigDecimal price, CarBodyType bodyType) {
        super(model, price);
        this.bodyType = bodyType;
        this.carManufacturer = carManufacturer;
    }

    @Override
    public String toString() {
        return "Car {" + bodyType + " from " +
                carManufacturer + ", id = '" + id + '\'' +
                ", model = '" + model + '\'' +
                ", price = " + price + "$" + '}';
    }
}