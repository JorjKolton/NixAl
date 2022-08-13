package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Car;
import com.nixal.ssobchenko.model.vehicle.CarBodyType;
import com.nixal.ssobchenko.model.vehicle.CarManufacturer;
import com.nixal.ssobchenko.repository.CarsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleServiceTest {

    private CarService target;
    private CarsRepository carsRepository;

    @BeforeEach
    void setUp() {
        carsRepository = Mockito.mock(CarsRepository.class);
        target = CarService.getInstance();
    }

    private Car createSimpleCar() {
        return new Car.Builder(700, CarManufacturer.BMW, new BigDecimal("35000"))
                .setCarBodyType(CarBodyType.CABRIOLET)
                .build();
    }

    @Test
    void createCars_negativeCount() {
        final List<Car> actual = target.createAndSave(-1);
        assertEquals(0, actual.size());
    }

    @Test
    void createCars_zeroCount() {
        final List<Car> actual = target.createAndSave(0);
        assertEquals(0, actual.size());
    }

    @Test
    void createCars() {
        target.createAndSave(5);
        Mockito.verify(carsRepository, Mockito.times(5))
                .saveAll(Mockito.any());
    }

    @Test
    void deleteCar_fail() {
        final Car car = createSimpleCar();
        final boolean actual = target.delete(car);
        Mockito.verify(carsRepository).delete(car.getId());
        assertFalse(actual);
    }

    @Test
    void deleteCar_success() {
        final Car car = target.createAndSaveCar(700, CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.when(carsRepository.delete(car.getId())).thenReturn(true);
        final boolean actual = target.delete(car);
        assertTrue(actual);
    }

    @Test
    void deleteCar_null() {
        final Car car = createSimpleCar();
        Mockito.when(carsRepository.delete(car.getId())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> target.delete(car));
    }
}