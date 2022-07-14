package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.Motorcycle;
import com.nixal.ssobchenko.model.MotorcycleBodyType;
import com.nixal.ssobchenko.model.MotorcycleManufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MotorcyclesRepositoryTest {

    private MotorcyclesRepository target;
    private Motorcycle motorcycle;

    @BeforeEach
    void setUp() {
        target = new MotorcyclesRepository();
        motorcycle = createSimpleMotorcycle();
        target.save(motorcycle);
    }

    private Motorcycle createSimpleMotorcycle() {
        return new Motorcycle("686", MotorcycleManufacturer.KAWASAKI, BigDecimal.ZERO, MotorcycleBodyType.MOTOCROSS);
    }

    @Test
    void getById_findOne() {
        final Motorcycle actual = target.getById(motorcycle.getId());
        assertNotNull(motorcycle);
        assertEquals(motorcycle.getId(), actual.getId());
    }

    @Test
    void getById_notFind() {
        final Motorcycle actual = target.getById("123");
        assertNull(actual);
    }

    @Test
    void getById_manyMotorcycles() {
        final Motorcycle otherMotorcycle = createSimpleMotorcycle();
        target.save(otherMotorcycle);
        final Motorcycle actual = target.getById(motorcycle.getId());
        assertNotNull(actual);
        assertEquals(motorcycle.getId(), actual.getId());
        assertNotEquals(otherMotorcycle.getId(), actual.getId());
    }

    @Test
    void getAll() {
        final List<Motorcycle> actual = target.getAll();
        assertNotNull(actual);
        assertEquals(1, actual.size());
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
        final Motorcycle actual = target.getById(motorcycle.getId());
        assertEquals(BigDecimal.valueOf(-1), actual.getPrice());
    }

    @Test
    void save_success_notChangePrice() {
        motorcycle.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(motorcycle);
        assertTrue(actual);
        final Motorcycle actualMotorcycle = target.getById(motorcycle.getId());
        assertEquals(BigDecimal.ONE, actualMotorcycle.getPrice());
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
        final Motorcycle actualMotorcycle = target.getById(motorcycle.getId());
        assertEquals(BigDecimal.TEN, actualMotorcycle.getPrice());
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