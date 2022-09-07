package com.nixal.ssobchenko.repository.hibernate;

import com.nixal.ssobchenko.config.HibernateFactoryUtil;
import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.repository.CrudRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernateBusesRepository implements CrudRepository<Bus> {
    private static HibernateBusesRepository instance;

    private final SessionFactory sessionFactory;

    private HibernateBusesRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static HibernateBusesRepository getInstance() {
        if (instance == null) {
            instance = new HibernateBusesRepository();
        }
        return instance;
    }

    @Override
    public Optional<Bus> findById(String id) {
        Session session = sessionFactory.openSession();
        Bus bus = session.get(Bus.class, id);
        session.close();
        return Optional.of(bus);
    }

    @Override
    public List<Bus> getAll() {
        Session session = sessionFactory.openSession();
        final List<Bus> buses = session.createQuery("from Bus ", Bus.class).list();
        session.close();
        return buses;
    }

    @Override
    public boolean save(Bus bus) {
        if (bus == null) {
            throw new IllegalArgumentException("Bus must be not null");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(bus);
        session.getTransaction().commit();
        session.close();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(bus);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Bus WHERE id = :id")
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
        session.createQuery("DELETE FROM Bus ").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}