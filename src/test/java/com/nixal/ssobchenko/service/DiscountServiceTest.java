package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Car;
import com.nixal.ssobchenko.model.vehicle.CarBodyType;
import com.nixal.ssobchenko.model.vehicle.CarManufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {
    private DiscountService<Car> target;
    private Car car;

    @BeforeEach
    void setUp() {
        car = createSimpleCar();
        target = new DiscountService<>(car);
    }

    public Car createSimpleCar() {
        return new Car(700, CarManufacturer.BMW, new BigDecimal("35000"), CarBodyType.CABRIOLET);
    }

    @Test
    void setPriceWithDiscount() {
        BigDecimal expectedPrice = new BigDecimal("29750");
        target.setPriceWithDiscount(15, 15);
        assertEquals(0, expectedPrice.compareTo(car.getPrice()));
    }

    @Test
    void increasePrice_withInt() {
        final int increase = 6000;
        BigDecimal expectedPrice = new BigDecimal("41000");
        target.increasePrice(increase);
        assertEquals(0, expectedPrice.compareTo(car.getPrice()));
    }

    @Test
    void increasePrice_withDouble() {
        final double increase = 6000.0;
        BigDecimal expectedPrice = new BigDecimal("41000");
        target.increasePrice(increase);
        assertEquals(0, expectedPrice.compareTo(car.getPrice()));
    }

    @Test
    void increasePrice_withFloat() {
        final float increase = 6000F;
        BigDecimal expectedPrice = new BigDecimal("41000");
        target.increasePrice(increase);
        assertEquals(0, expectedPrice.compareTo(car.getPrice()));
    }
}