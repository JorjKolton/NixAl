package com.ssobchenko;

import com.ssobchenko.service.ShopAnalytics;
import com.ssobchenko.service.ShopAnalyticsPrinter;
import com.ssobchenko.service.ShopService;

import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ShopService SHOP_SERVICE = ShopService.getInstance();
    private static final ShopAnalyticsPrinter SHOP_ANALYTICS_PRINTER = new ShopAnalyticsPrinter(new ShopAnalytics(SHOP_SERVICE));

    public static void main(String[] args) {
        SHOP_SERVICE.getDevicesFromFileAndPutThemToShop();
        System.out.println("Enter retail limit");
        final int limit = SCANNER.nextInt();

        for (int i = 0; i < 15; i++) {
            SHOP_SERVICE.createRandomInvoice(limit);
        }

        SHOP_ANALYTICS_PRINTER.showNumberOfItemsSoldByCategory();
        System.out.println("#".repeat(20));

        SHOP_ANALYTICS_PRINTER.showSumOfTheSmallestInvoiceAndCustomerInfo();
        System.out.println("#".repeat(20));

        SHOP_ANALYTICS_PRINTER.showSumOfAllInvoices();
        System.out.println("#".repeat(20));

        SHOP_ANALYTICS_PRINTER.showNumberOfInvoicesWithRetailCategory();
        System.out.println("#".repeat(20));

        SHOP_ANALYTICS_PRINTER.showInvoicesThatContainOnlyOneTypeOfDevice();
        System.out.println("#".repeat(20));

        SHOP_ANALYTICS_PRINTER.showFirstThreeInvoicesByDate();
        System.out.println("#".repeat(20));

        SHOP_ANALYTICS_PRINTER.showInvoicesWhereCustomerYoungerThanEighteen();
        System.out.println("#".repeat(20));

        SHOP_SERVICE.invoicesSortByAgeByItemCountsBySum();
    }
}