package com.nixal.ssobchenko.repository.hibernate;

import com.nixal.ssobchenko.config.HibernateFactoryUtil;
import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import com.nixal.ssobchenko.repository.CrudRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernateMotorcyclesRepository implements CrudRepository<Motorcycle> {
    private static HibernateMotorcyclesRepository instance;

    private final SessionFactory sessionFactory;

    private HibernateMotorcyclesRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static HibernateMotorcyclesRepository getInstance() {
        if (instance == null) {
            instance = new HibernateMotorcyclesRepository();
        }
        return instance;
    }

    @Override
    public Optional<Motorcycle> findById(String id) {
        Session session = sessionFactory.openSession();
        Motorcycle motorcycle = session.get(Motorcycle.class, id);
        session.close();
        return Optional.of(motorcycle);
    }

    @Override
    public List<Motorcycle> getAll() {
        Session session = sessionFactory.openSession();
        final List<Motorcycle> motorcycles = session.createQuery("from Motorcycle ", Motorcycle.class).list();
        session.close();
        return motorcycles;
    }

    @Override
    public boolean save(Motorcycle motorcycle) {
        if (motorcycle == null) {
            throw new IllegalArgumentException("Motorcycle must be not null");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(motorcycle);
        session.getTransaction().commit();
        session.close();
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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(motorcycle);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Motorcycle WHERE id = :id")
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
        session.createQuery("DELETE FROM Motorcycle ").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}