package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import com.nixal.ssobchenko.model.vehicle.MotorcycleBodyType;
import com.nixal.ssobchenko.model.vehicle.MotorcycleManufacturer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MotorcyclesRepositoryTest {

    private MotorcyclesRepository target;
    private Motorcycle motorcycle;

    @BeforeEach
    void setUp() {
        target = MotorcyclesRepository.getInstance();
        motorcycle = createSimpleMotorcycle();
        target.save(motorcycle);
    }

    @AfterEach
    void tearDown() {
        target.deleteAll();
    }

    private Motorcycle createSimpleMotorcycle() {
        return new Motorcycle(686, MotorcycleManufacturer.KAWASAKI, BigDecimal.ZERO, MotorcycleBodyType.MOTOCROSS);
    }

    @Test
    void getAll() {
        final List<Motorcycle> actual = target.getAll();
        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void getById_findOne() {
        final Optional<Motorcycle> actual = target.findById(motorcycle.getId());
        assertTrue(actual.isPresent());
        assertEquals(motorcycle.getId(), actual.get().getId());
    }

    @Test
    void getById_notFind() {
        final Optional<Motorcycle> actual = target.findById("123");
        assertFalse(actual.isPresent());
    }

    @Test
    void getById_manyMotorcycles() {
        final Motorcycle otherMotorcycle = createSimpleMotorcycle();
        target.save(otherMotorcycle);
        final Optional<Motorcycle> actual = target.findById(motorcycle.getId());
        assertTrue(actual.isPresent());
        assertEquals(motorcycle.getId(), actual.get().getId());
        assertNotEquals(otherMotorcycle.getId(), actual.get().getId());
    }

    @Test
    void save_success() {
        final boolean actual = target.save(motorcycle);
        assertTrue(actual);
    }

    @Test
    void save_fail() {
        assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void save_success_changePrice() {
        target.save(motorcycle);
        final Optional<Motorcycle> actual = target.findById(motorcycle.getId());
        assertTrue(actual.isPresent());
        assertEquals(BigDecimal.valueOf(-1), actual.get().getPrice());
    }

    @Test
    void save_success_notChangePrice() {
        motorcycle.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(motorcycle);
        assertTrue(actual);
        final Optional<Motorcycle> actualMotorcycle = target.findById(motorcycle.getId());
        assertTrue(actualMotorcycle.isPresent());
        assertEquals(BigDecimal.ONE, actualMotorcycle.get().getPrice());
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
        final boolean actual = target.saveAll(List.of(createSimpleMotorcycle()));
        assertTrue(actual);
    }

    @Test
    void update_notFound() {
        final Motorcycle otherMotorcycle = createSimpleMotorcycle();
        final boolean actual = target.update(otherMotorcycle);
        assertFalse(actual);
    }

    @Test
    void update() {
        motorcycle.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(motorcycle);
        assertTrue(actual);
        final Optional<Motorcycle> actualMotorcycle = target.findById(motorcycle.getId());
        assertTrue(actualMotorcycle.isPresent());
        assertEquals(BigDecimal.TEN, actualMotorcycle.get().getPrice());
    }

    @Test
    void delete_success() {
        final boolean actual = target.delete(motorcycle.getId());
        assertTrue(actual);
    }

    @Test
    void delete_fail() {
        final Motorcycle otherMotorcycle = createSimpleMotorcycle();
        final boolean actual = target.delete(otherMotorcycle.getId());
        assertFalse(actual);
    }
}