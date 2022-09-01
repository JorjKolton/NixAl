package com.nixal.ssobchenko.repository;

import com.nixal.ssobchenko.config.JDBCConfig;
import com.nixal.ssobchenko.model.vehicle.Bus;
import com.nixal.ssobchenko.model.vehicle.BusManufacturer;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBBusesRepository implements CrudRepository<Bus> {

    private static DBBusesRepository instance;

    private final Connection connection;

    private DBBusesRepository() {
        connection = JDBCConfig.getConnection();
    }

    public static DBBusesRepository getInstance() {
        if (instance == null) {
            instance = new DBBusesRepository();
        }
        return instance;
    }

    @Override
    public Optional<Bus> findById(String id) {
        final String sql = "SELECT * FROM public.Buses WHERE id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToObject(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bus> getAll() {
        final List<Bus> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM public.Buses");
            while (resultSet.next()) {
                result.add(mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean save(Bus bus) {
        if (bus == null) {
            throw new IllegalArgumentException("Buses must be not null");
        }
        if (bus.getPrice().equals(BigDecimal.ZERO)) {
            bus.setPrice(BigDecimal.valueOf(-1));
        }
        final String sql = "INSERT INTO public.Buses (id, vehicle_type, model, manufacturer, price, seats, created)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            mapObjectToRow(preparedStatement, bus);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        final String sql = "UPDATE public.Buses " +
                "SET id = ?, vehicle_type = ?, model = ?, manufacturer = ?, price = ?, seats = ?,created = ? " +
                "WHERE id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(8, bus.getId());
            mapObjectToRow(preparedStatement, bus);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        final String sql = "DELETE FROM public.Buses WHERE id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        final String sql = "DELETE FROM public.Buses";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private Bus mapRowToObject(final ResultSet resultSet) {
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
    private void mapObjectToRow(final PreparedStatement preparedStatement, final Bus bus) {
        preparedStatement.setString(1, bus.getId());
        preparedStatement.setString(2, "bus");
        preparedStatement.setInt(3, bus.getModel());
        preparedStatement.setString(4, bus.getBusManufacturer().name());
        preparedStatement.setBigDecimal(5, bus.getPrice());
        preparedStatement.setInt(6, bus.getNumberOfSeats());
        preparedStatement.setTimestamp(7, Timestamp.valueOf(bus.getCreated()));
        preparedStatement.addBatch();
    }
}