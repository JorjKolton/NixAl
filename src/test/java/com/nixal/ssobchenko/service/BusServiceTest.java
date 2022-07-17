package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.Bus;
import com.nixal.ssobchenko.model.BusManufacturer;
import com.nixal.ssobchenko.repository.BusesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BusServiceTest {
    private BusService target;
    private BusesRepository busesRepository;

    @BeforeEach
    void setUp() {
        busesRepository = Mockito.mock(BusesRepository.class);
        target = new BusService(busesRepository);
    }

    private Bus createSimpleBus() {
        return new Bus("890", BusManufacturer.SCANIA, new BigDecimal("150000"), 52);
    }

    @Test
    void createBuses_negativeCount() {
        final List<Bus> actual = target.createAndSaveBuses(-1);
        assertEquals(0, actual.size());
    }

    @Test
    void createBuses_zeroCount() {
        final List<Bus> actual = target.createAndSaveBuses(0);
        assertEquals(0, actual.size());
    }

    @Test
    void createBuses() {
        target.createAndSaveBuses(5);
        Mockito.verify(busesRepository, Mockito.times(5))
                .saveAll(Mockito.any());
    }

    @Test
    void createBus_positive() {
        final List<Bus> actual = List
                .of(target.createAndSaveBus("318", BusManufacturer.TOYOTA, "183000", 43));
        assertEquals(1, actual.size());
        Mockito.verify(busesRepository).save(actual.get(0));
    }

    @Test
    void createBus() {
        final Bus actual = createSimpleBus();
        Mockito.when(busesRepository.findById(Mockito.argThat(arg -> arg == null || arg.length() > 5)))
                .thenReturn(Optional.of(actual));
        final Optional<Bus> expected = busesRepository.findById("Expected");
        assertTrue(expected.isPresent());
        assertEquals(expected.get(), actual);
    }

    @Test
    void changeBus_fail() {
        Bus bus = createSimpleBus();
        Mockito.when(busesRepository.update(bus)).thenCallRealMethod();
        final boolean expected = target.changeBus(bus, "890", BusManufacturer.SCANIA, "150000", 52);
        assertFalse(expected);
    }

    @Test
    void changeBus_success() {
        ArgumentCaptor<Bus> captor = ArgumentCaptor.forClass(Bus.class);
        Bus bus = target.createAndSaveBus("318", BusManufacturer.TOYOTA, "183000", 43);
        Mockito.when(busesRepository.update(bus)).thenReturn(true);
        boolean expected = target.changeBus(bus, "324", BusManufacturer.TOYOTA, "183000", 43);
        Mockito.verify(busesRepository).update(captor.capture());
        assertTrue(expected);
        assertEquals("324", bus.getModel());
        assertEquals(bus, captor.getValue());
    }

    @Test
    void deleteBus_fail() {
        final Bus bus = createSimpleBus();
        final boolean actual = target.deleteBus(bus);
        Mockito.verify(busesRepository).delete(bus.getId());
        assertFalse(actual);
    }

    @Test
    void deleteBus_success() {
        final Bus bus = target.createAndSaveBus("318", BusManufacturer.TOYOTA, "183000", 43);
        Mockito.when(busesRepository.delete(bus.getId())).thenReturn(true);
        final boolean actual = target.deleteBus(bus);
        assertTrue(actual);
    }

    @Test
    void deleteBus_null() {
        final Bus bus = createSimpleBus();
        Mockito.when(busesRepository.delete(bus.getId())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> target.deleteBus(bus));
    }
}