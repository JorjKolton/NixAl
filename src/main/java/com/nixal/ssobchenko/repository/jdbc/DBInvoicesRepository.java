package com.nixal.ssobchenko.repository.jdbc;

import com.nixal.ssobchenko.config.JDBCConfig;
import com.nixal.ssobchenko.model.vehicle.*;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        final String sql = "INSERT INTO public.Invoices (invoice_id, created, price) VALUES (?, ?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, invoice.getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(invoice.getCreated()));
            preparedStatement.setBigDecimal(3, invoice.getPrice());
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

    public Optional<Invoice> findById(String id) {
        final String sql = "SELECT * FROM public.Invoices WHERE invoice_id = ? ";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapInvoiceRowToObject(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCountOfInvoices() {
        final String sql = "SELECT COUNT(*) AS count FROM public.Invoices ";
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

    public List<Invoice> getAll() {
        final List<Invoice> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM public.Invoices");
            while (resultSet.next()) {
                result.add(mapInvoiceRowToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<Invoice> getAllInvoicesWherePriceBiggerThan(int price) {
        final String sql = "SELECT * FROM public.Invoices WHERE price > ? ";
        final List<Invoice> result = new ArrayList<>();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, price);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(mapInvoiceRowToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean updateInvoiceCreationTime(String id, LocalDateTime time) {
        final String sql = "UPDATE public.invoices SET created = ? WHERE invoice_id = ? ";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(time));
            preparedStatement.setString(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Integer> groupInvoicesBySum() {
        final String sql = "SELECT price, COUNT(*) FROM Invoices GROUP BY price ORDER BY price DESC ";
        final Map<String, Integer> result = new HashMap<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.put(resultSet.getBigDecimal("price").toString(),
                        Integer.parseInt(resultSet.getString("count")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
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

    @SneakyThrows
    private Invoice mapInvoiceRowToObject(final ResultSet resultSet) {
        final String invoiceId = resultSet.getString("invoice_id");
        final List<Vehicle> vehicles = new ArrayList<>();
        vehicles.addAll(getAllCars(invoiceId));
        vehicles.addAll(getAllBuses(invoiceId));
        vehicles.addAll(getAllMotorcycles(invoiceId));
        return new Invoice.Builder(vehicles)
                .setId(invoiceId)
                .setCreated(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }

    private List<Vehicle> getAllCars(String invoiceId) {
        final String sql = "SELECT * FROM public.Cars WHERE invoice_id = " + "'" + invoiceId + "'";
        final List<Vehicle> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(mapCarRowToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<Vehicle> getAllBuses(String invoiceId) {
        final String sql = "SELECT * FROM public.Buses WHERE invoice_id = " + "'" + invoiceId + "'";
        final List<Vehicle> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(mapBusRowToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<Vehicle> getAllMotorcycles(String invoiceId) {
        final String sql = "SELECT * FROM public.Motorcycles WHERE invoice_id = " + "'" + invoiceId + "'";
        final List<Vehicle> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(mapMotorcycleRowToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @SneakyThrows
    private Car mapCarRowToObject(final ResultSet resultSet) {
        return new Car.Builder(
                resultSet.getInt("model"),
                CarManufacturer.valueOf(resultSet.getString("manufacturer").toUpperCase()),
                resultSet.getBigDecimal("price"))
                .setId(resultSet.getString("id"))
                .setCarBodyType(CarBodyType.valueOf(resultSet.getString("body_type").toUpperCase()))
                .setCreated(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }

    @SneakyThrows
    private Bus mapBusRowToObject(final ResultSet resultSet) {
        return new Bus.Builder(
                resultSet.getInt("model"),
                BusManufacturer.valueOf(resultSet.getString("manufacturer").toUpperCase()),
                resultSet.getBigDecimal("price"))
                .setId(resultSet.getString("id"))
                .setNumberOfSeats(resultSet.getInt("seats"))
                .setCreated(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }

    @SneakyThrows
    private Motorcycle mapMotorcycleRowToObject(final ResultSet resultSet) {
        return new Motorcycle.Builder(
                resultSet.getInt("model"),
                MotorcycleManufacturer.valueOf(resultSet.getString("manufacturer").toUpperCase()),
                resultSet.getBigDecimal("price"))
                .setId(resultSet.getString("id"))
                .setMotorcycleBodyType(MotorcycleBodyType.valueOf(resultSet.getString("body_type").toUpperCase()))
                .setCreated(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }
}