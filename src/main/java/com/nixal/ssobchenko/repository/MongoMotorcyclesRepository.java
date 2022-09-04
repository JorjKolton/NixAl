package com.nixal.ssobchenko.repository;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nixal.ssobchenko.config.GsonUtil;
import com.nixal.ssobchenko.config.MongoUtil;
import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoMotorcyclesRepository implements CrudRepository<Motorcycle> {
    private final MongoCollection<Document> motorcycles;
    private final Gson gson;

    private static MongoMotorcyclesRepository instance;

    private MongoMotorcyclesRepository() {
        motorcycles = MongoUtil.connect("Vehicles").getCollection("Motorcycles");
        gson = new GsonUtil().getGson();
    }

    public static MongoMotorcyclesRepository getInstance() {
        if (instance == null) {
            instance = new MongoMotorcyclesRepository();
        }
        return instance;
    }

    @Override
    public Optional<Motorcycle> findById(String id) {
        return Optional.of(motorcycles.find(new Document("_id", id))
                .map(motorcycle -> gson.fromJson(motorcycle.toJson(), Motorcycle.class))
                .into(new ArrayList<>()).get(0));
    }

    @Override
    public List<Motorcycle> getAll() {
        return motorcycles.find()
                .map(motorcycle -> gson.fromJson(motorcycle.toJson(), Motorcycle.class))
                .into(new ArrayList<>());
    }

    @Override
    public boolean save(Motorcycle motorcycle) {
        motorcycles.insertOne(mapToDoc(motorcycle));
        return true;
    }

    @Override
    public boolean saveAll(List<Motorcycle> motorcycles) {
        if (motorcycles == null) {
            return false;
        }
        motorcycles.forEach(this::save);
        return true;
    }

    @Override
    public boolean update(Motorcycle motorcycle) {
        final Document filter = new Document();
        filter.append("_id", motorcycle.getId());
        final Document update = mapToDoc(motorcycle);
        final Document doc = new Document();
        doc.append("$set", update);
        motorcycles.updateOne(filter, doc);
        return true;
    }

    @Override
    public boolean delete(String id) {
        motorcycles.deleteOne(new Document("_id", id));
        return true;
    }

    @Override
    public void deleteAll() {
        motorcycles.drop();
    }

    private Document mapToDoc(Motorcycle motorcycle) {
        return Document.parse(gson.toJson(motorcycle));
    }
}