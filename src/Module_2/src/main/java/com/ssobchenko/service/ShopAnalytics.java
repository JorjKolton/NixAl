package com.ssobchenko.service;

import com.ssobchenko.model.Invoice;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShopAnalytics {
    private final ShopService shopService;

    public ShopAnalytics(ShopService shopService) {
        this.shopService = shopService;
    }

    public Map<String, Integer> numberOfItemsSoldByCategory() {
        return shopService.getInvoices().stream()
                .map(Invoice::getListOfProducts)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().size(),
                        Integer::sum));
    }

    public Optional<Invoice> theSmallestInvoice() {
        return shopService.getInvoices()
                .stream()
                .min(Comparator.comparing(Invoice::getFullInvoicePrice));
    }

    public int sumOfAllInvoices() {
        return shopService.getInvoices().stream()
                .mapToInt(Invoice::getFullInvoicePrice)
                .sum();
    }

    public long numberOfInvoicesWithRetailCategory() {
        return shopService.getInvoices().stream()
                .filter(invoice -> invoice.getType().name().equalsIgnoreCase("retail"))
                .count();
    }

    public List<Invoice> invoicesThatContainOnlyOneTypeOfDevice() {
        return shopService.getInvoices().stream()
                .filter(invoice -> invoice
                        .getListOfProducts()
                        .size() == 1)
                .toList();
    }

    public List<Invoice> firstThreeInvoicesByDate() {
        return shopService.getInvoices().stream()
                .sorted(Comparator.comparing(Invoice::getCreated))
                .limit(3)
                .toList();
    }

    public List<Invoice> invoicesWhereCustomerYoungerThanEighteen() {
        return shopService.getInvoices().stream()
                .filter(invoice -> invoice
                        .getCustomer()
                        .getAge() < 18)
                .toList();
    }
}