package com.nixal.ssobchenko.repository.jdbc;

import com.nixal.ssobchenko.config.JDBCConfig;
import com.nixal.ssobchenko.model.vehicle.Motorcycle;
import com.nixal.ssobchenko.model.vehicle.MotorcycleBodyType;
import com.nixal.ssobchenko.model.vehicle.MotorcycleManufacturer;
import com.nixal.ssobchenko.repository.CrudRepository;
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

public class DBMotorcyclesRepository implements CrudRepository<Motorcycle> {

    private static DBMotorcyclesRepository instance;

    private final Connection connection;

    private DBMotorcyclesRepository() {
        connection = JDBCConfig.getConnection();
    }

    public static DBMotorcyclesRepository getInstance() {
        if (instance == null) {
            instance = new DBMotorcyclesRepository();
        }
        return instance;
    }

    @Override
    public Optional<Motorcycle> findById(String id) {
        final String sql = "SELECT * FROM public.Motorcycles WHERE id = ?";
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
    public List<Motorcycle> getAll() {
        final List<Motorcycle> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM public.Motorcycles");
            while (resultSet.next()) {
                result.add(mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean save(Motorcycle motorcycle) {
        if (motorcycle == null) {
            throw new IllegalArgumentException("Motorcycle must be not null");
        }
        if (motorcycle.getPrice().equals(BigDecimal.ZERO)) {
            motorcycle.setPrice(BigDecimal.valueOf(-1));
        }
        final String sql = "INSERT INTO public.Motorcycles (id, vehicle_type, model, body_type, manufacturer, price, created)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            mapObjectToRow(preparedStatement, motorcycle);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        final String sql = "UPDATE public.Motorcycles " +
                "SET id = ?, vehicle_type = ?, model = ?, body_type = ?, manufacturer = ?, price = ?, created = ? " +
                "WHERE id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(8, motorcycle.getId());
            mapObjectToRow(preparedStatement, motorcycle);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        final String sql = "DELETE FROM public.Motorcycles WHERE id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        final String sql = "DELETE FROM public.Motorcycles";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private Motorcycle mapRowToObject(final ResultSet resultSet) {
        return new Motorcycle.Builder(
                resultSet.getInt("model"),
                MotorcycleManufacturer.valueOf(resultSet.getString("manufacturer").toUpperCase()),
                resultSet.getBigDecimal("price"))
                .setId(resultSet.getString("id"))
                .setMotorcycleBodyType(MotorcycleBodyType.valueOf(resultSet.getString("body_type").toUpperCase()))
                .setCreated(resultSet.getTimestamp("created").toLocalDateTime())
                .build();
    }

    @SneakyThrows
    private void mapObjectToRow(final PreparedStatement preparedStatement, final Motorcycle motorcycle) {
        preparedStatement.setString(1, motorcycle.getId());
        preparedStatement.setString(2, "motorcycle");
        preparedStatement.setInt(3, motorcycle.getModel());
        preparedStatement.setString(4, motorcycle.getBodyType().name());
        preparedStatement.setString(5, motorcycle.getMotorcycleManufacturer().name());
        preparedStatement.setBigDecimal(6, motorcycle.getPrice());
        preparedStatement.setTimestamp(7, Timestamp.valueOf(motorcycle.getCreated()));
        preparedStatement.addBatch();
    }
}