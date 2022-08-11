package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Car;
import com.nixal.ssobchenko.model.vehicle.CarBodyType;
import com.nixal.ssobchenko.model.vehicle.CarManufacturer;
import com.nixal.ssobchenko.repository.CarsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

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
    void createCar_positive() {
        final List<Car> actual = List
                .of(target.createAndSaveCar(700, CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET));
        assertEquals(1, actual.size());
        Mockito.verify(carsRepository).save(actual.get(0));
    }

    @Test
    void createCar() {
        final Car actual = createSimpleCar();
        Mockito.when(carsRepository.findById(Mockito.argThat(arg -> arg == null || arg.length() > 5)))
                .thenReturn(Optional.of(actual));
        final Optional<Car> expected = carsRepository.findById("Expected");
        assertTrue(expected.isPresent());
        assertEquals(expected.get(), actual);
    }

    @Test
    void getOrCreateCar_get() {
        final Car actual = target.createAndSaveCar(700, CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.when(carsRepository.findById(actual.getId())).thenReturn(Optional.of(actual));
        Optional<Car> optionalCar = Optional.of(target.getOrCreateCar(actual.getId()));
        assertEquals(actual, optionalCar.get());
    }

    @Test
    void getOrCreateCar_create() {
        final Car actual = createSimpleCar();
        Mockito.when(carsRepository.findById(actual.getId())).thenReturn(Optional.of(createSimpleCar()));
        Optional<Car> optionalCar = Optional.of(target.getOrCreateCar(actual.getId()));
        assertNotEquals(actual, optionalCar.get());
    }

    @Test
    void getPriceForCarIfItLessThan_success() {
        final Car actual = target.createAndSaveCar(700, CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.when(carsRepository.findById(actual.getId())).thenReturn(Optional.of(actual));
        final BigDecimal price = target.getPriceForCarIfItLessThan(actual.getId(), "50000");
        final boolean expected = price.compareTo(new BigDecimal("50000")) < 0;
        assertTrue(expected);
    }

    @Test
    void getPriceForCarIfItLessThan_fail() {
        final Car actual = target.createAndSaveCar(700, CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        final BigDecimal price = target.getPriceForCarIfItLessThan(actual.getId(), "34000");
        Mockito.verify(carsRepository).findById(actual.getId());
        assertEquals(BigDecimal.ZERO, price);
    }

    @Test
    void getCarForId_success() {
        final Car actual = target.createAndSaveCar(700, CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.when(carsRepository.findById(actual.getId())).thenReturn(Optional.of(actual));
        Optional<Car> optionalCar = Optional.of(target.getCarForId(actual.getId()));
        assertEquals(actual, optionalCar.get());
    }

    @Test
    void getCarForId_fail() {
        final Car actual = createSimpleCar();
        Mockito.when(carsRepository.findById(actual.getId())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> target.getCarForId(actual.getId()));
    }

    @Test
    void changeCar_fail() {
        Car car = createSimpleCar();
        Mockito.when(carsRepository.update(car)).thenCallRealMethod();
        final boolean expected = target.changeCar(car, 657, CarManufacturer.AUDI, "45400", CarBodyType.COUPE);
        assertFalse(expected);
    }

    @Test
    void changeCar_success() {
        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);
        Car car = target.createAndSaveCar(700, CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.when(carsRepository.update(car)).thenReturn(true);
        boolean expected = target.changeCar(car, 657, CarManufacturer.BMW, "35000", CarBodyType.CABRIOLET);
        Mockito.verify(carsRepository).update(captor.capture());
        assertTrue(expected);
        assertEquals(657, car.getModel());
        assertEquals(car, captor.getValue());
    }

    @Test
    void getCarWithManufacturer() {
        final Car car = target.createAndSaveCar(700, CarManufacturer.AUDI, "35000", CarBodyType.CABRIOLET);
        final CarManufacturer carManufacturer = CarManufacturer.AUDI;
        Mockito.when(carsRepository.findById(car.getId())).thenReturn(Optional.of(car));
        final Car actual = target.getCarWithManufacturer(car.getId(), carManufacturer);
        assertEquals(carManufacturer, actual.getCarManufacturer());
    }
}