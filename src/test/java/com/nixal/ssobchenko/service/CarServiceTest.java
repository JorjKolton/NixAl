package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Car;
import com.nixal.ssobchenko.model.CarBodyType;
import com.nixal.ssobchenko.model.CarManufacturer;
import com.nixal.ssobchenko.repository.CarsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    private CarService target;
    private CarsRepository carsRepository;

    @BeforeEach
    void setUp() {
        carsRepository = Mockito.mock(CarsRepository.class);
        target = new CarService(carsRepository);
    }

    private Car createSimpleCar() {
        return new Car("700", CarManufacturer.BMW,  new BigDecimal("35000"), CarBodyType.CABRIOLET);
    }

    @Test
    void createCars_negativeCount() {
        final List<Car> actual = target.createAndSaveCars(-1);
        assertEquals(0, actual.size());
    }

    @Test
    void createCars_zeroCount() {
        final List<Car> actual = target.createAndSaveCars(0);
        assertEquals(0, actual.size());
    }

    @Test
    void createCars() {
        target.createAndSaveCars(5);
        Mockito.verify(carsRepository, Mockito.times(5))
                .saveAll(Mockito.any());
    }

    @Test
    void createCar_positive() {
        final List<Car> actual = List
                .of(target.createAndSaveCar("700", CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET));
        assertEquals(1, actual.size());
        Mockito.verify(carsRepository).save(actual.get(0));
    }

    @Test
    void createCar() {
        final Car actual = createSimpleCar();
        Mockito.when(carsRepository.getById(Mockito.argThat(arg -> arg == null || arg.length() > 5))).thenReturn(actual);
        assertEquals(carsRepository.getById("Expected"), actual);
    }

    @Test
    void changeCar_fail() {
        Car car = createSimpleCar();
        Mockito.when(carsRepository.update(car)).thenCallRealMethod();
        final boolean expected = target.changeCar(car, "657", CarManufacturer.AUDI, "45400", CarBodyType.COUPE);
        assertFalse(expected);
    }

    @Test
    void changeCar_success() {
        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);
        Car car = target.createAndSaveCar("700", CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.when(carsRepository.update(car)).thenReturn(true);
        boolean expected = target.changeCar(car, "657", CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.verify(carsRepository).update(captor.capture());
        assertTrue(expected);
        assertEquals("657", car.getModel());
        assertEquals(car, captor.getValue());
    }

    @Test
    void deleteCar_fail() {
        final Car car = createSimpleCar();
        final boolean actual = target.deleteCar(car);
        Mockito.verify(carsRepository).delete(car.getId());
        assertFalse(actual);
    }

    @Test
    void deleteCar_success() {
        final Car car = target.createAndSaveCar("700", CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.when(carsRepository.delete(car.getId())).thenReturn(true);
        final boolean actual = target.deleteCar(car);
        assertTrue(actual);
    }

    @Test
    void deleteCar_null() {
        final Car car = createSimpleCar();
        Mockito.when(carsRepository.delete(car.getId())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> target.deleteCar(car));
    }
}