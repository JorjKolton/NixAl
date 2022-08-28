package com.nixal.ssobchenko.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLScript {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static final String ID = "id VARCHAR NOT NULL, ";
    private static final String MODEL = "model INTEGER NOT NULL,";
    private static final String MANUFACTURER = "manufacturer VARCHAR(30) NOT NULL, ";
    private static final String PRICE = "price NUMERIC NOT NULL, ";
    private static final String CREATED = "created TIMESTAMP WITHOUT TIME ZONE NOT NULL, ";
    private static final String PRIMARY_KEY = "PRIMARY KEY (id)";
    private static final String INVOICE_ID = "invoice_id VARCHAR(50), ";
    private static final String FOREIGN_KEY = "CONSTRAINT fk_invoices FOREIGN KEY (invoice_id) REFERENCES invoices(id), ";

    public static void createAllTables() {
        createInvoicesTable();
        createCarsTable();
        createBusesTable();
        createMotorcyclesTable();
    }

    private static void createInvoicesTable() {
        final String sql = "CREATE TABLE IF NOT EXISTS public.Invoices (" +
                ID + CREATED + PRIMARY_KEY + ")";
        try (final Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createCarsTable() {
        final String sql = "CREATE TABLE IF NOT EXISTS public.Cars (" +
                ID + "vehicle_type VARCHAR(3) NOT NULL DEFAULT 'car', " +
                MODEL + "body_type VARCHAR(20) NOT NULL," +
                MANUFACTURER + PRICE + CREATED + INVOICE_ID +
                FOREIGN_KEY + PRIMARY_KEY + ")";
        try (final Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createMotorcyclesTable() {
        final String sql = "CREATE TABLE IF NOT EXISTS public.Motorcycles (" +
                ID + "vehicle_type VARCHAR(10) NOT NULL DEFAULT 'motorcycle', " +
                MODEL + "body_type VARCHAR(20) NOT NULL," +
                MANUFACTURER + PRICE + CREATED + INVOICE_ID +
                FOREIGN_KEY + PRIMARY_KEY + ")";
        try (final Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createBusesTable() {
        final String sql = "CREATE TABLE IF NOT EXISTS public.Buses (" +
                ID + "vehicle_type VARCHAR(3) NOT NULL DEFAULT 'bus', " +
                MODEL + MANUFACTURER + PRICE + "seats INTEGER NOT NULL," +
                CREATED + INVOICE_ID + FOREIGN_KEY + PRIMARY_KEY + ")";
        try (final Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}