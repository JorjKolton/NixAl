package com.nixal.ssobchenko.service;

import com.nixal.ssobchenko.model.vehicle.Invoice;
import com.nixal.ssobchenko.model.vehicle.Vehicle;
import com.nixal.ssobchenko.repository.HibernateInvoicesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class InvoiceService {

    private static InvoiceService instance;

    private final HibernateInvoicesRepository repository;

    private InvoiceService() {
        repository = HibernateInvoicesRepository.getInstance();
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService();
        }
        return instance;
    }

    public Invoice createAndSaveInvoice(List<Vehicle> vehicles) {
        final Invoice invoice = new Invoice.Builder(vehicles).build();
        repository.save(invoice);
        return invoice;
    }

    public int countOfInvoices() {
        return repository.getCountOfInvoices();
    }

    public boolean updateInvoiceCreationTime(String id, LocalDateTime time) {
        return repository.updateInvoiceCreationTime(id, time);
    }

    public Invoice getInvoiceForId(String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Can't find invoice with id " + id));
    }

    public List<Invoice> getAll() {
        return repository.getAll();
    }

    public List<Invoice> getInvoicesWherePriceBiggerThan(int price) {
        return repository.getAllInvoicesWherePriceBiggerThan(price);
    }

    public Map<String, Integer> groupInvoicesBySum() {
        return repository.groupInvoicesBySum();
    }
}