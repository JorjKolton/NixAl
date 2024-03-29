package com.nixal.ssobchenko.repository.hibernate;

import com.nixal.ssobchenko.config.HibernateFactoryUtil;
import com.nixal.ssobchenko.model.vehicle.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HibernateInvoicesRepository {

    private static HibernateInvoicesRepository instance;

    private final SessionFactory sessionFactory;

    private HibernateInvoicesRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static HibernateInvoicesRepository getInstance() {
        if (instance == null) {
            instance = new HibernateInvoicesRepository();
        }
        return instance;
    }

    public boolean save(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must be not null");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(invoice);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public Optional<Invoice> findById(String id) {
        Session session = sessionFactory.openSession();
        Invoice invoice = session.get(Invoice.class, id);
        session.close();
        return Optional.of(invoice);
    }

    public int getCountOfInvoices() {
        Session session = sessionFactory.openSession();
        int result = (int) session.createSQLQuery("SELECT count(id) as count from Invoices")
                .addScalar("count", IntegerType.INSTANCE)
                .uniqueResult();
        session.close();
        return result;
    }

    public List<Invoice> getAll() {
        Session session = sessionFactory.openSession();
        final List<Invoice> invoices = session.createQuery("from Invoice ", Invoice.class).list();
        session.close();
        return invoices;
    }

    public List<Invoice> getAllInvoicesWherePriceBiggerThan(int price) {
        Session session = sessionFactory.openSession();
        TypedQuery<Invoice> invoiceQuery = session.createQuery("from Invoice where price > :price ", Invoice.class)
                .setParameter("price", new BigDecimal(String.valueOf(price)));
        List<Invoice> result = invoiceQuery.getResultList();
        session.close();
        return result;
    }

    public boolean updateInvoiceCreationTime(String id, LocalDateTime time) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Invoice invoice = session.get(Invoice.class, id);
        invoice.setCreated(time);
        session.update(invoice);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public Map<String, Integer> groupInvoicesBySum() {
        Map<String, Integer> result = new HashMap<>();
        Session session = sessionFactory.openSession();
        TypedQuery<Object[]> query = session
                .createQuery("select i.price, count(i.price) from Invoice i group by i.price", Object[].class);
        List<Object[]> list = query.getResultList();
        list.forEach(item -> result.put(item[0].toString(), Integer.valueOf(item[1].toString())));
        session.close();
        return result;
    }
}