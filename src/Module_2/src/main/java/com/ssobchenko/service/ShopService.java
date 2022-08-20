package com.ssobchenko.service;

import com.ssobchenko.model.Invoice;
import com.ssobchenko.model.device.Device;
import com.ssobchenko.model.device.Telephone;
import com.ssobchenko.model.device.Television;
import com.ssobchenko.util.CSVReader;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Getter
public class ShopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);
    private static final String TELEVISION = "Television";
    private static final String TELEPHONE = "Telephone";
    private static final Random RANDOM = new Random();
    private final Map<String, List<? extends Device>> devices;
    private final List<Invoice> invoices;

    private static ShopService instance;

    private ShopService() {
        devices = new HashMap<>();
        invoices = new ArrayList<>();
    }

    public static ShopService getInstance() {
        if (instance == null) {
            instance = new ShopService();
        }
        return instance;
    }

    public void getDevicesFromFileAndPutThemToShop() {
        devices.putAll(CSVReader.getAllDevicesFromFile());
    }

    public void createRandomInvoice(int retailLimit) {
        final int groupsOfDevices = 2;
        final List<Television> tvList = new ArrayList<>();
        final List<Telephone> phoneList = new ArrayList<>();

        for (int i = 0; i < RANDOM.nextInt(1, 6); i++) {
            int choiceDevicesCategories = RANDOM.nextInt(groupsOfDevices);
            if (choiceDevicesCategories == 0 && !devices.get(TELEVISION).isEmpty()) {
                phoneList.add(getTelephoneForInvoice());
            } else {
                tvList.add(getTelevisionForInvoice());
            }
        }

        final Invoice invoice = new Invoice.Builder(PersonService.createRandomCustomer(),
                LocalDateTime.now(),
                getDevicesForOneInvoice(tvList, phoneList),
                retailLimit)
                .build();
        writeInvoiceLog(invoice);
        invoices.add(invoice);
    }

    private static final Comparator<Invoice> AGE_COMPARATOR = Comparator.comparingInt(o -> o.getCustomer().getAge());

    private static final Comparator<Invoice> NUMBER_OF_ITEMS_COMPARATOR = Comparator
            .comparingInt(o -> o.getListOfProducts().values().stream().mapToInt(List::size).sum());

    public void invoicesSortByAgeByItemCountsBySum() {
        invoices.stream()
                .sorted(AGE_COMPARATOR.reversed()
                        .thenComparing(NUMBER_OF_ITEMS_COMPARATOR)
                        .thenComparing(Invoice::getFullInvoicePrice))
                .forEach(System.out::println);
    }

    private Telephone getTelephoneForInvoice() {
        return (Telephone) devices.get(TELEPHONE).get(RANDOM.nextInt(devices.get(TELEPHONE).size()));
    }

    private Television getTelevisionForInvoice() {
        return (Television) devices.get(TELEVISION).get(RANDOM.nextInt(devices.get(TELEVISION).size()));
    }

    private Map<String, List<? extends Device>> getDevicesForOneInvoice(List<Television> tvList,
                                                                        List<Telephone> phoneList) {
        final Map<String, List<? extends Device>> invoice = new HashMap<>();
        if (!tvList.isEmpty()) {
            invoice.put(TELEVISION, tvList);
        }
        if (!phoneList.isEmpty()) {
            invoice.put(TELEPHONE, phoneList);
        }
        return invoice;
    }

    private void writeInvoiceLog(Invoice invoice) {
        try (final FileWriter fw = new FileWriter("src/Module_2/logs/invoiceLog.txt", true)) {
            fw.write("[" + LocalDateTime.now() + "]" + "\n" +
                    invoice + "\n" + "#".repeat(20) + "\n");
        } catch (final IOException e) {
            LOGGER.error("IOException: ", e);
        }
    }
}