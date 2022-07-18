package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Motorcycle;
import com.nixal.ssobchenko.model.MotorcycleBodyType;
import com.nixal.ssobchenko.model.MotorcycleManufacturer;
import com.nixal.ssobchenko.repository.MotorcyclesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MotorcycleServiceTest {

    private MotorcycleService target;
    private MotorcyclesRepository motorcyclesRepository;

    @BeforeEach
    void setUp() {
        motorcyclesRepository = Mockito.mock(MotorcyclesRepository.class);
        target = new MotorcycleService(motorcyclesRepository);
    }

    private Motorcycle createSimpleMotorcycle() {
        return new Motorcycle("101",
                MotorcycleManufacturer.KAWASAKI, new BigDecimal("8000"), MotorcycleBodyType.MOTOCROSS);
    }

    @Test
    void createMotorcycles_negativeCount() {
        final List<Motorcycle> actual = target.createAndSaveMotorcycles(-1);
        assertEquals(0, actual.size());
    }

    @Test
    void createMotorcycles_zeroCount() {
        final List<Motorcycle> actual = target.createAndSaveMotorcycles(0);
        assertEquals(0, actual.size());
    }

    @Test
    void createMotorcycles() {
        target.createAndSaveMotorcycles(5);
        Mockito.verify(motorcyclesRepository, Mockito.times(5))
                .saveAll(Mockito.any());
    }

    @Test
    void createMotorcycle_positive() {
        final List<Motorcycle> actual = List.of(target.createAndSaveMotorcycle("101",
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
        final boolean expected = target.changeMotorcycle(motorcycle, "107",
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS);
        assertFalse(expected);
    }

    @Test
    void changeMotorcycle_success() {
        ArgumentCaptor<Motorcycle> captor = ArgumentCaptor.forClass(Motorcycle.class);
        Motorcycle motorcycle = target.createAndSaveMotorcycle("101",
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS);
        Mockito.when(motorcyclesRepository.update(motorcycle)).thenReturn(true);
        boolean expected = target.changeMotorcycle(motorcycle, "107",
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS);
        Mockito.verify(motorcyclesRepository).update(captor.capture());
        assertTrue(expected);
        assertEquals("107", motorcycle.getModel());
        assertEquals(motorcycle, captor.getValue());
    }

    @Test
    void deleteMotorcycle_fail() {
        final Motorcycle motorcycle = createSimpleMotorcycle();
        final boolean actual = target.deleteMotorcycle(motorcycle);
        Mockito.verify(motorcyclesRepository).delete(motorcycle.getId());
        assertFalse(actual);
    }

    @Test
    void deleteMotorcycle_success() {
        final Motorcycle motorcycle = target.createAndSaveMotorcycle("101",
                MotorcycleManufacturer.KAWASAKI, "8000", MotorcycleBodyType.MOTOCROSS);
        Mockito.when(motorcyclesRepository.delete(motorcycle.getId())).thenReturn(true);
        final boolean actual = target.deleteMotorcycle(motorcycle);
        assertTrue(actual);
    }

    @Test
    void deleteMotorcycle_null() {
        final Motorcycle motorcycle = createSimpleMotorcycle();
        Mockito.when(motorcyclesRepository.delete(motorcycle.getId())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> target.deleteMotorcycle(motorcycle));
    }
}