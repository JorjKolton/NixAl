package com.nixal.ssobchenko.repository;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nixal.ssobchenko.config.GsonUtil;
import com.nixal.ssobchenko.config.MongoUtil;
import com.nixal.ssobchenko.model.vehicle.Bus;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoBusesRepository implements CrudRepository<Bus> {
    private final MongoCollection<Document> buses;
    private final Gson gson;

    private static MongoBusesRepository instance;

    private MongoBusesRepository() {
        buses = MongoUtil.connect("Vehicles").getCollection("Buses");
        gson = new GsonUtil().getGson();
    }

    public static MongoBusesRepository getInstance() {
        if (instance == null) {
            instance = new MongoBusesRepository();
        }
        return instance;
    }

    @Override
    public Optional<Bus> findById(String id) {
        return Optional.of(buses.find(new Document("_id", id))
                .map(bus -> gson.fromJson(bus.toJson(), Bus.class))
                .into(new ArrayList<>()).get(0));
    }

    @Override
    public List<Bus> getAll() {
        return buses.find()
                .map(bus -> gson.fromJson(bus.toJson(), Bus.class))
                .into(new ArrayList<>());
    }

    @Override
    public boolean save(Bus bus) {
        buses.insertOne(mapToDoc(bus));
        return true;
    }

    @Override
    public boolean saveAll(List<Bus> buses) {
        if (buses == null) {
            return false;
        }
        buses.forEach(this::save);
        return true;
    }

    @Override
    public boolean update(Bus bus) {
        final Document filter = new Document();
        filter.append("_id", bus.getId());
        final Document update = mapToDoc(bus);
        final Document doc = new Document();
        doc.append("$set", update);
        buses.updateOne(filter, doc);
        return true;
    }

    @Override
    public boolean delete(String id) {
        buses.deleteOne(new Document("_id", id));
        return true;
    }

    @Override
    public void deleteAll() {
        buses.drop();
    }

    private Document mapToDoc(Bus bus) {
        return Document.parse(gson.toJson(bus));
    }
}