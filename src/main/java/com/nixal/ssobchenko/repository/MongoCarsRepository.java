package com.nixal.ssobchenko.repository;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nixal.ssobchenko.config.GsonUtil;
import com.nixal.ssobchenko.config.MongoUtil;
import com.nixal.ssobchenko.model.vehicle.Car;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoCarsRepository implements CrudRepository<Car> {
    private final MongoCollection<Document> cars;
    private final Gson gson;

    private static MongoCarsRepository instance;

    private MongoCarsRepository() {
        cars = MongoUtil.connect("Vehicles").getCollection("Cars");
        gson = new GsonUtil().getGson();
    }

    public static MongoCarsRepository getInstance() {
        if (instance == null) {
            instance = new MongoCarsRepository();
        }
        return instance;
    }

    @Override
    public Optional<Car> findById(String id) {
        return Optional.of(cars.find(new Document("_id", id))
                .map(car -> gson.fromJson(car.toJson(), Car.class))
                .into(new ArrayList<>()).get(0));
    }

    @Override
    public List<Car> getAll() {
        return cars.find()
                .map(car -> gson.fromJson(car.toJson(), Car.class))
                .into(new ArrayList<>());
    }

    @Override
    public boolean save(Car car) {
        cars.insertOne(mapToDoc(car));
        return true;
    }

    @Override
    public boolean saveAll(List<Car> cars) {
        if (cars == null) {
            return false;
        }
        cars.forEach(this::save);
        return true;
    }

    @Override
    public boolean update(Car car) {
        final Document filter = new Document();
        filter.append("_id", car.getId());
        final Document update = mapToDoc(car);
        final Document doc = new Document();
        doc.append("$set", update);
        cars.updateOne(filter, doc);
        return true;
    }

    @Override
    public boolean delete(String id) {
        cars.deleteOne(new Document("_id", id));
        return true;
    }

    @Override
    public void deleteAll() {
        cars.drop();
    }

    private Document mapToDoc(Car car) {
        return Document.parse(gson.toJson(car));
    }
}