package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.Car;
import com.nixal.ssobchenko.model.CarBodyType;
import com.nixal.ssobchenko.model.CarManufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarsRepositoryTest {

    private CarsRepository target;
    private Car car;

    @BeforeEach
    void setUp() {
        target = new CarsRepository();
        car = createSimpleCar();
        target.save(car);
    }

    private Car createSimpleCar() {
        return new Car("686", CarManufacturer.FORD, BigDecimal.ZERO, CarBodyType.SPORT_CAR);
    }

    @Test
    void getById_findOne() {
        final Car actual = target.getById(car.getId());
        assertNotNull(car);
        assertEquals(car.getId(), actual.getId());
    }

    @Test
    void getById_notFind() {
        final Car actual = target.getById("123");
        assertNull(actual);
    }

    @Test
    void getById_manyCars() {
        final Car otherCar = createSimpleCar();
        target.save(otherCar);
        final Car actual = target.getById(car.getId());
        assertNotNull(actual);
        assertEquals(car.getId(), actual.getId());
        assertNotEquals(otherCar.getId(), actual.getId());
    }

    @Test
    void getAll() {
        final List <Car> actual = target.getAll();
        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void save_success() {
        final boolean actual = target.save(car);
        assertTrue(actual);
    }

    @Test
    void save_fail() {
        assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void save_success_changePrice() {
        target.save(car);
        final Car actual = target.getById(car.getId());
        assertEquals(BigDecimal.valueOf(-1), actual.getPrice());
    }

    @Test
    void save_success_notChangePrice() {
        car.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(car);
        assertTrue(actual);
        final Car actualAuto = target.getById(car.getId());
        assertEquals(BigDecimal.ONE, actualAuto.getPrice());
    }

    @Test
    void saveAll_null() {
        final boolean actual = target.saveAll(null);
        assertFalse(actual);
    }

    @Test
    void saveAll_emptyList() {
        final boolean actual = target.saveAll(Collections.emptyList());
        assertFalse(actual);
    }

    @Test
    void save_all() {
        final boolean actual = target.saveAll(List.of(createSimpleCar()));
        assertTrue(actual);
    }

    @Test
    void update_notFound() {
        final Car otherCar = createSimpleCar();
        final boolean actual = target.update(otherCar);
        assertFalse(actual);
    }

    @Test
    void update() {
        car.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(car);
        assertTrue(actual);
        final Car actualCar = target.getById(car.getId());
        assertEquals(BigDecimal.TEN, actualCar.getPrice());
    }

    @Test
    void delete_success() {
        final boolean actual = target.delete(car.getId());
        assertTrue(actual);
    }

    @Test
    void delete_fail() {
        final Car otherCar = createSimpleCar();
        final boolean actual = target.delete(otherCar.getId());
        assertFalse(actual);
    }
}