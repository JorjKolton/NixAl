package com.nixal.ssobchenko.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Motorcycle extends Vehicle {
    private MotorcycleBodyType bodyType;
    private MotorcycleManufacturer motorcycleManufacturer;

    public Motorcycle(int model, MotorcycleManufacturer motorcycleManufacturer, BigDecimal price, MotorcycleBodyType bodyType) {
        super(model, price, VehicleType.MOTORCYCLE);
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