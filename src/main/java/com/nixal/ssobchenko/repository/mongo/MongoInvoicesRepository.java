package com.nixal.ssobchenko.repository.mongo;

import com.google.gson.Gson;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.nixal.ssobchenko.config.GsonUtil;
import com.nixal.ssobchenko.config.MongoUtil;
import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.model.vehicle.Car;
import com.nixal.ssobchenko.model.vehicle.Invoice;
import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import com.nixal.ssobchenko.model.vehicle.Vehicle;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.sort;

public class MongoInvoicesRepository {
    private final MongoCollection<Document> invoices;
    private final Gson gson;

    private static MongoInvoicesRepository instance;

    private MongoInvoicesRepository() {
        invoices = MongoUtil.connect("Vehicles").getCollection("Invoices");
        gson = new GsonUtil().getGson();
    }

    public static MongoInvoicesRepository getInstance() {
        if (instance == null) {
            instance = new MongoInvoicesRepository();
        }
        return instance;
    }

    public boolean save(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must be not null");
        }
        Document parse = mapToDoc(invoice);
        final List<Document> vehicles = invoice.getVehicles()
                .stream()
                .map(v -> Document.parse(gson.toJson(v)))
                .toList();
        parse.append("Vehicles", vehicles);
        invoices.insertOne(parse);
        return true;
    }

    public Optional<Invoice> findById(String id) {
        @SuppressWarnings("unchecked")
        List<Document> documents = (List<Document>) Objects
                .requireNonNull(invoices.find(new Document("_id", id)).first())
                .get("Vehicles");
        List<Vehicle> vehicles = new ArrayList<>();
        for (Document document : documents) {
            if (document.containsValue("CAR")) {
                vehicles.add(gson.fromJson(document.toJson(), Car.class));
            }
            if (document.containsValue("BUS")) {
                vehicles.add(gson.fromJson(document.toJson(), Bus.class));
            }
            if (document.containsValue("MOTORCYCLE")) {
                vehicles.add(gson.fromJson(document.toJson(), Motorcycle.class));
            }
        }
        Invoice found = invoices.find(new Document("_id", id))
                .map(invoice -> gson.fromJson(invoice.toJson(), Invoice.class))
                .into(new ArrayList<>()).get(0);
        found.setVehicles(vehicles);
        return Optional.of(found);
    }

    public int getCountOfInvoices() {
        return (int) invoices.countDocuments();
    }

    public List<Invoice> getAll() {
        return invoices.find()
                .map(invoice -> findById(invoice.getString("_id")).get())
                .into(new ArrayList<>());
    }

    public List<Invoice> getAllInvoicesWherePriceBiggerThan(int price) {
        Bson filter = Filters.gt("price", price);
        return invoices.find(filter)
                .map(invoice -> findById(invoice.getString("_id")).get())
                .into(new ArrayList<>());
    }

    public boolean updateInvoiceCreationTime(String id, LocalDateTime time) {
        final Document filter = new Document();
        filter.append("_id", id);
        final Document update = new Document();
        update.append("created", time.toString());
        final Document doc = new Document();
        doc.append("$set", update);
        invoices.updateOne(filter, doc);
        return true;
    }

    public Map<String, Integer> groupInvoicesBySum() {
        Map<String, Integer> result = new HashMap<>();
        AggregateIterable<Document> aggregate = invoices.aggregate(Arrays.asList(
                group("$price", Accumulators.sum("count", 1)),
                sort(Sorts.descending("count"))));
        for (Document iter : aggregate) {
            result.put(String.valueOf(iter.get("_id", Integer.class)), (int) iter.get("count"));
        }
        return result;
    }

    private Document mapToDoc(Invoice invoice) {
        return Document.parse(gson.toJson(invoice));
    }
}