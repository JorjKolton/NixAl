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
}