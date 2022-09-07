package com.nixal.ssobchenko;

import com.nixal.ssobchenko.model.vehicle.Invoice;
import com.nixal.ssobchenko.model.vehicle.Vehicle;
import com.nixal.ssobchenko.service.BusService;
import com.nixal.ssobchenko.service.CarService;
import com.nixal.ssobchenko.service.InvoiceService;
import com.nixal.ssobchenko.service.MotorcycleService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final CarService CAR_SERVICE = CarService.getInstance();
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final MotorcycleService MOTORCYCLE_SERVICE = MotorcycleService.getInstance();
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();

    public static void main(String[] args) {
//        UserInputStart.start();

        List<Vehicle> vehicles1 = new ArrayList<>();
        vehicles1.addAll(CAR_SERVICE.createAndSave(2));
        vehicles1.addAll(BUS_SERVICE.createAndSave(2));
        vehicles1.addAll(MOTORCYCLE_SERVICE.createAndSave(2));

        List<Vehicle> vehicles2 = new ArrayList<>();
        vehicles2.addAll(CAR_SERVICE.createAndSave(2));
        vehicles2.addAll(BUS_SERVICE.createAndSave(2));
        vehicles2.addAll(MOTORCYCLE_SERVICE.createAndSave(2));

        List<Vehicle> vehicles3 = new ArrayList<>();
        vehicles3.addAll(CAR_SERVICE.createAndSave(2));
        vehicles3.addAll(BUS_SERVICE.createAndSave(2));
        vehicles3.addAll(MOTORCYCLE_SERVICE.createAndSave(2));

        INVOICE_SERVICE.createAndSaveInvoice(vehicles1);
        INVOICE_SERVICE.createAndSaveInvoice(vehicles2);
        INVOICE_SERVICE.createAndSaveInvoice(vehicles3);

        System.out.println("Count of invoices = " + INVOICE_SERVICE.countOfInvoices());
        System.out.println("Count of invoices where price bigger than 20000 = " +
                INVOICE_SERVICE.getInvoicesWherePriceBiggerThan(20000).size());
        System.out.println(INVOICE_SERVICE.groupInvoicesBySum());

        INVOICE_SERVICE.getAll()
                .stream()
                .map(Invoice::getVehicles)
                .forEach(System.out::println);

    }
}