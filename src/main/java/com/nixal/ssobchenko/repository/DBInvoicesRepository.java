package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.config.JDBCConfig;
import com.nixal.ssobchenko.model.vehicle.Invoice;
import com.nixal.ssobchenko.model.vehicle.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBInvoicesRepository {

    private static DBInvoicesRepository instance;

    private final Connection connection;

    private DBInvoicesRepository() {
        connection = JDBCConfig.getConnection();
    }

    public static DBInvoicesRepository getInstance() {
        if (instance == null) {
            instance = new DBInvoicesRepository();
        }
        return instance;
    }

    public boolean save(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must be not null");
        }
        final String sql = "INSERT INTO public.Invoices (id, created) VALUES (?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, invoice.getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(invoice.getCreated()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        final String invoiceID = invoice.getId();
        final String carSQL = "UPDATE public.Cars SET invoice_id = ? WHERE id = ?";
        final String busSQL = "UPDATE public.Buses SET invoice_id = ? WHERE id = ?";
        final String motorcycleSQL = "UPDATE public.Motorcycles SET invoice_id = ? WHERE id = ?";

        invoice.getVehicles().forEach(vehicle -> {
            if (vehicle.getType().name().equalsIgnoreCase("car")) {
                saveVehicleFromInvoice(carSQL, invoiceID, vehicle);
            }
            if (vehicle.getType().name().equalsIgnoreCase("bus")) {
                saveVehicleFromInvoice(busSQL, invoiceID, vehicle);
            }
            if (vehicle.getType().name().equalsIgnoreCase("motorcycle")) {
                saveVehicleFromInvoice(motorcycleSQL, invoiceID, vehicle);
            }
        });
        return true;
    }

    public int getCountOfInvoices() {
        final String sql = "SELECT COUNT(*) AS count FROM public.Invoices";
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean updateInvoiceCreationTime(String id, LocalDateTime time) {
        final String sql = "UPDATE public.invoices SET created = ? WHERE id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(time));
            preparedStatement.setString(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveVehicleFromInvoice(String sql, String invoiceID, Vehicle vehicle) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, invoiceID);
            preparedStatement.setString(2, vehicle.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
