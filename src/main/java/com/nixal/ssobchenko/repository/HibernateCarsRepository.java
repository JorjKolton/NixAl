package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.config.HibernateFactoryUtil;
import com.nixal.ssobchenko.model.vehicle.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernateCarsRepository implements CrudRepository<Car>{
    private static HibernateCarsRepository instance;

    private final SessionFactory sessionFactory;

    private HibernateCarsRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static HibernateCarsRepository getInstance() {
        if (instance == null) {
            instance = new HibernateCarsRepository();
        }
        return instance;
    }

    @Override
    public Optional<Car> findById(String id) {
        Session session = sessionFactory.openSession();
        Car car = session.get(Car.class, id);
        session.close();
        return Optional.of(car);
    }

    @Override
    public List<Car> getAll() {
        Session session = sessionFactory.openSession();
        final List<Car> cars = session.createQuery("from Car", Car.class).list();
        session.close();
        return cars;
    }

    @Override
    public boolean save(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car must be not null");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(car);
        session.getTransaction().commit();
        session.close();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(car);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Car WHERE id = :id")
                .setParameter("id", id)
                        .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Car ").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}