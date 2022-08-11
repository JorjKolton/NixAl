package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.model.vehicle.BusManufacturer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BusesRepositoryTest {

    private BusesRepository target;
    private Bus bus;

    @BeforeEach
    void setUp() {
        target = BusesRepository.getInstance();
        bus = createSimpleBus();
        target.save(bus);
    }

    @AfterEach
    void tearDown() {
        target.deleteAll();
    }

    private Bus createSimpleBus() {
        return new Bus.Builder(686, BusManufacturer.MERCEDES, BigDecimal.ZERO)
                .setNumberOfSeats(50)
                .build();
    }

    @Test
    void getById_findOne() {
        final Optional<Bus> actual = target.findById(bus.getId());
        assertTrue(actual.isPresent());
        assertEquals(bus.getId(), actual.get().getId());
    }

    @Test
    void getById_notFind() {
        final Optional<Bus> actual = target.findById("123");
        assertFalse(actual.isPresent());
    }

    @Test
    void getById_manyBuses() {
        final Bus otherCar = createSimpleBus();
        target.save(otherCar);
        final Optional<Bus> actual = target.findById(bus.getId());
        assertTrue(actual.isPresent());
        assertEquals(bus.getId(), actual.get().getId());
        assertNotEquals(otherCar.getId(), actual.get().getId());
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
        final Optional<Bus> actual = target.findById(bus.getId());
        assertTrue(actual.isPresent());
        assertEquals(BigDecimal.valueOf(-1), actual.get().getPrice());
    }

    @Test
    void save_success_notChangePrice() {
        bus.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(bus);
        assertTrue(actual);
        final Optional<Bus> actualBus = target.findById(bus.getId());
        assertTrue(actualBus.isPresent());
        assertEquals(BigDecimal.ONE, actualBus.get().getPrice());
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
        final Optional<Bus> actualBus = target.findById(bus.getId());
        assertTrue(actualBus.isPresent());
        assertEquals(BigDecimal.TEN, actualBus.get().getPrice());
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