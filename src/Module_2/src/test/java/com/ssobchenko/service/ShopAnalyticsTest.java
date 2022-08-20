package com.ssobchenko.service;

import com.ssobchenko.model.Customer;
import com.ssobchenko.model.Invoice;
import com.ssobchenko.model.device.Device;
import com.ssobchenko.model.device.Telephone;
import com.ssobchenko.model.device.Television;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopAnalyticsTest {
    private ShopAnalytics target;
    private ShopService shopService;
    private List<Invoice> invoices;

    @BeforeEach
    void setUp() {
        shopService = Mockito.mock(ShopService.class);
        target = new ShopAnalytics(shopService);
        invoices = createInvoices(2000);
    }

    private List<Invoice> createInvoices(int limit) {
        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(createInvoice1(limit));
        invoices.add(createInvoice2(limit));
        invoices.add(createInvoice3(limit));
        return invoices;
    }

    private Customer createCustomer(int age) {
        return new Customer
                .Builder(age)
                .build();
    }

    private Telephone createTelephone(String series, String screenType, String model, int price) {
        return new Telephone.Builder(series, screenType, model)
                .setPrice(price)
                .build();
    }

    private Television createTelevision(String series, String screenType, String country, int price, int diagonal) {
        return new Television.Builder(series, screenType, country)
                .setDiagonal(diagonal)
                .setPrice(price)
                .build();
    }

    private Invoice createInvoice1(int limit) {
        Map<String, List<? extends Device>> listOfProducts = new HashMap<>();
        List<Television> tvList = new ArrayList<>();
        tvList.add(createTelevision("Prime", "Oled", "China", 800, 32));
        listOfProducts.put("Television", tvList);
        return new Invoice.Builder(createCustomer(17),
                LocalDateTime.now()
                , listOfProducts, limit).build();
    }

    private Invoice createInvoice2(int limit) {
        Map<String, List<? extends Device>> listOfProducts = new HashMap<>();
        List<Television> tvList = new ArrayList<>();
        List<Telephone> phoneList = new ArrayList<>();
        tvList.add(createTelevision("A86", "LCD", "Poland", 600, 55));
        phoneList.add(createTelephone("12", "Oled", "Apple", 1300));
        listOfProducts.put("Television", tvList);
        listOfProducts.put("Telephone", phoneList);
        return new Invoice.Builder(createCustomer(25),
                LocalDateTime.now()
                , listOfProducts, limit).build();
    }

    private Invoice createInvoice3(int limit) {
        Map<String, List<? extends Device>> listOfProducts = new HashMap<>();
        List<Television> tvList = new ArrayList<>();
        List<Telephone> phoneList = new ArrayList<>();
        tvList.add(createTelevision("A86", "LCD", "Poland", 900, 55));
        phoneList.add(createTelephone("S21", "Qled", "Samsung", 1400));
        phoneList.add(createTelephone("12", "Oled", "Apple", 1300));
        listOfProducts.put("Television", tvList);
        listOfProducts.put("Telephone", phoneList);
        return new Invoice.Builder(createCustomer(25),
                LocalDateTime.now()
                , listOfProducts, limit).build();
    }

    @Test
    void numberOfItemsSoldByCategory() {
        Mockito.when(shopService.getInvoices()).thenReturn(invoices);
        final Map<String, Integer> actual = target.numberOfItemsSoldByCategory();
        assertEquals(3, actual.get("Television"));
        assertEquals(3, actual.get("Telephone"));
    }

    @Test
    void theSmallestInvoice() {
        Mockito.when(shopService.getInvoices()).thenReturn(invoices);
        final Optional<Invoice> actual = target.theSmallestInvoice();
        Mockito.verify(shopService).getInvoices();
        actual.ifPresent(invoice -> assertEquals(invoices.get(0), actual.get()));
    }

    @Test
    void sumOfAllInvoices() {
        Mockito.when(shopService.getInvoices()).thenReturn(invoices);
        final int actual = target.sumOfAllInvoices();
        assertEquals(6300, actual);
    }

    @Test
    void numberOfInvoicesWithRetailCategory() {
        Mockito.when(shopService.getInvoices()).thenReturn(invoices);
        final long actual = target.numberOfInvoicesWithRetailCategory();
        assertEquals(1, actual);
    }

    @Test
    void invoicesThatContainOnlyOneTypeOfDevice() {
        Mockito.when(shopService.getInvoices()).thenReturn(invoices);
        final List<Invoice> actual = target.invoicesThatContainOnlyOneTypeOfDevice();
        assertEquals(1, actual.size());
        assertTrue(actual.contains(invoices.get(0)));
    }

    @Test
    void firstThreeInvoicesByDate() {
        Mockito.when(shopService.getInvoices()).thenReturn(invoices);
        final List<Invoice> actual = target.firstThreeInvoicesByDate();
        assertEquals(3, actual.size());
        assertTrue(actual.containsAll(invoices));
    }

    @Test
    void invoicesWhereCustomerYoungerThanEighteen() {
        Mockito.when(shopService.getInvoices()).thenReturn(invoices);
        final List<Invoice> actual = target.invoicesWhereCustomerYoungerThanEighteen();
        assertEquals(1, actual.size());
        assertTrue(actual.contains(invoices.get(0)));
    }
}