package com.nixal.ssobchenko.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Motorcycle extends Vehicle {
    private MotorcycleBodyType bodyType;
    private MotorcycleManufacturer motorcycleManufacturer;

    public Motorcycle(String model, MotorcycleManufacturer motorcycleManufacturer, BigDecimal price, MotorcycleBodyType bodyType) {
        super(model, price);
        this.bodyType = bodyType;
        this.motorcycleManufacturer = motorcycleManufacturer;
    }

    @Override
    public String toString() {
        return "Motorcycle {" + bodyType + " from " +
                motorcycleManufacturer + ", id = '" + id + '\'' +
                ", model = '" + model + '\'' +
                ", price = " + price + "$" + '}';
    }
}