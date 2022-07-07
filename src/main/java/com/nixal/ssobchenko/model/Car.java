package com.nixal.ssobchenko.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Car extends Vehicle {
    private BodyType bodyType;

    public Car(String model, Manufacturer manufacturer, BigDecimal price, BodyType bodyType) {
        super(model, manufacturer, price);
        this.bodyType = bodyType;
    }

    @Override
    public String toString() {
        return "Car {" + bodyType + " from " +
                manufacturer + ", id = '" + id + '\'' +
                ", model = '" + model + '\'' +
                ", price = " + price + "$" + '}';
    }
}