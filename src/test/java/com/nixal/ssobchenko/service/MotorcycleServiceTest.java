package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import com.nixal.ssobchenko.model.vehicle.MotorcycleBodyType;
import com.nixal.ssobchenko.model.vehicle.MotorcycleManufacturer;
import com.nixal.ssobchenko.repository.MotorcyclesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MotorcycleServiceTest {

    private MotorcycleService target;
    private MotorcyclesRepository motorcyclesRepository;

    @BeforeEach
    void setUp() {
        motorcyclesRepository = Mockito.mock(MotorcyclesRepository.class);
        target = MotorcycleService.getInstance();
    }

    private Motorcycle createSimpleMotorcycle() {
        return new Motorcycle.Builder(101, MotorcycleManufacturer.KAWASAKI, new BigDecimal("8000"))
                .setMotorcycleBodyType(MotorcycleBodyType.MOTOCROSS)
                .build();
    }

    @Test
    void createMotorcycle_positive() {
        final List<Motorcycle> actual = List.of(target.createAndSaveMotorcycle(101,
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS));
        assertEquals(1, actual.size());
        Mockito.verify(motorcyclesRepository).save(actual.get(0));
    }

    @Test
    void createMotorcycle() {
        final Motorcycle actual = createSimpleMotorcycle();
        Mockito.when(motorcyclesRepository.findById(Mockito.argThat(arg -> arg == null || arg.length() > 5)))
                .thenReturn(Optional.of(actual));
        final Optional<Motorcycle> expected = motorcyclesRepository.findById("Expected");
        assertTrue(expected.isPresent());
        assertEquals(expected.get(), actual);
    }

    @Test
    void changeMotorcycle_fail() {
        Motorcycle motorcycle = createSimpleMotorcycle();
        Mockito.when(motorcyclesRepository.update(motorcycle)).thenCallRealMethod();
        final boolean expected = target.changeMotorcycle(motorcycle, 107,
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS);
        assertFalse(expected);
    }

    @Test
    void changeMotorcycle_success() {
        ArgumentCaptor<Motorcycle> captor = ArgumentCaptor.forClass(Motorcycle.class);
        Motorcycle motorcycle = target.createAndSaveMotorcycle(101,
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS);
        Mockito.when(motorcyclesRepository.update(motorcycle)).thenReturn(true);
        boolean expected = target.changeMotorcycle(motorcycle, 107,
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS);
        Mockito.verify(motorcyclesRepository).update(captor.capture());
        assertTrue(expected);
        assertEquals(107, motorcycle.getModel());
        assertEquals(motorcycle, captor.getValue());
    }
}