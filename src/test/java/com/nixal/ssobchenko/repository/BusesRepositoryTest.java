package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.Bus;
import com.nixal.ssobchenko.model.BusManufacturer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BusesRepositoryTest {

    private BusesRepository target;
    private Bus bus;

    @BeforeEach
    void setUp() {
        target = new BusesRepository();
        bus = createSimpleBus();
        target.save(bus);
    }

    private Bus createSimpleBus() {
        return new Bus("686", BusManufacturer.MERCEDES, BigDecimal.ZERO, 50);
    }

    @Test
    void getById_findOne() {
        final Bus actual = target.getById(bus.getId());
        assertNotNull(bus);
        assertEquals(bus.getId(), actual.getId());
    }

    @Test
    void getById_notFind() {
        final Bus actual = target.getById("123");
        assertNull(actual);
    }

    @Test
    void getById_manyBuses() {
        final Bus otherCar = createSimpleBus();
        target.save(otherCar);
        final Bus actual = target.getById(bus.getId());
        assertNotNull(actual);
        assertEquals(bus.getId(), actual.getId());
        assertNotEquals(otherCar.getId(), actual.getId());
    }

    @Test
    void getAll() {
        final List<Bus> actual = target.getAll();
        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void save_success() {
        final boolean actual = target.save(bus);
        assertTrue(actual);
    }

    @Test
    void save_fail() {
        assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void save_success_changePrice() {
        target.save(bus);
        final Bus actual = target.getById(bus.getId());
        assertEquals(BigDecimal.valueOf(-1), actual.getPrice());
    }

    @Test
    void save_success_notChangePrice() {
        bus.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(bus);
        assertTrue(actual);
        final Bus actualBus = target.getById(bus.getId());
        assertEquals(BigDecimal.ONE, actualBus.getPrice());
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
        final boolean actual = target.saveAll(List.of(createSimpleBus()));
        assertTrue(actual);
    }

    @Test
    void update_notFound() {
        final Bus otherBus = createSimpleBus();
        final boolean actual = target.update(otherBus);
        assertFalse(actual);
    }

    @Test
    void update() {
        bus.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(bus);
        assertTrue(actual);
        final Bus actualBus = target.getById(bus.getId());
        assertEquals(BigDecimal.TEN, actualBus.getPrice());
    }

    @Test
    void delete_success() {
        final boolean actual = target.delete(bus.getId());
        assertTrue(actual);
    }

    @Test
    void delete_fail() {
        final Bus otherBus = createSimpleBus();
        final boolean actual = target.delete(otherBus.getId());
        assertFalse(actual);
    }
}