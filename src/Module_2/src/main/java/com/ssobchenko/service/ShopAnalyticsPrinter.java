package com.ssobchenko.service;

public class ShopAnalyticsPrinter {
    private final ShopAnalytics shopAnalytics;

    public ShopAnalyticsPrinter(ShopAnalytics shopAnalytics) {
        this.shopAnalytics = shopAnalytics;
    }

    public void showSumOfTheSmallestInvoiceAndCustomerInfo() {
        shopAnalytics.theSmallestInvoice().ifPresentOrElse(
                invoice -> {
                    System.out.println("Sum of the smallest invoice is " + invoice.getFullInvoicePrice() + "$");
                    System.out.println(invoice.getCustomer());
                },
                () -> System.out.println("No any invoices yet"));
    }

    public void showNumberOfItemsSoldByCategory() {
        System.out.println("Number of items sold by category:");
        shopAnalytics.numberOfItemsSoldByCategory()
                .forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public void showSumOfAllInvoices() {
        System.out.println("Sum of all invoices: " + shopAnalytics.sumOfAllInvoices() + "$");
    }

    public void showNumberOfInvoicesWithRetailCategory() {
        System.out.println("Number of invoices with retail category: " +
                shopAnalytics.numberOfInvoicesWithRetailCategory());
    }

    public void showInvoicesThatContainOnlyOneTypeOfDevice() {
        System.out.println("Invoices that contain only one type of device:");
        shopAnalytics.invoicesThatContainOnlyOneTypeOfDevice().forEach(System.out::println);
    }

    public void showFirstThreeInvoicesByDate() {
        System.out.println("First three invoices by date:");
        shopAnalytics.firstThreeInvoicesByDate().forEach(System.out::println);
    }

    public void showInvoicesWhereCustomerYoungerThanEighteen() {
        System.out.println("Invoices where customer younger than eighteen:");
        shopAnalytics.invoicesWhereCustomerYoungerThanEighteen().forEach(System.out::println);
    }
}