package com.nixal.ssobchenko.repository.jdbc;

import com.nixal.ssobchenko.config.JDBCConfig;
import com.nixal.ssobchenko.model.vehicle.Car;
import com.nixal.ssobchenko.model.vehicle.CarBodyType;
import com.nixal.ssobchenko.model.vehicle.CarManufacturer;
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

public class DBCarsRepository implements CrudRepository<Car> {

    private static DBCarsRepository instance;

    private final Connection connection;

    private DBCarsRepository() {
        connection = JDBCConfig.getConnection();
    }

    public static DBCarsRepository getInstance() {
        if (instance == null) {
            instance = new DBCarsRepository();
        }
        return instance;
    }

    @Override
    public Optional<Car> findById(String id) {
        final String sql = "SELECT * FROM public.Cars WHERE id = ?";
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
    public List<Car> getAll() {
        final List<Car> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM public.Cars");
            while (resultSet.next()) {
                result.add(mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean save(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car must be not null");
        }
        if (car.getPrice().equals(BigDecimal.ZERO)) {
            car.setPrice(BigDecimal.valueOf(-1));
        }
        final String sql = "INSERT INTO public.Cars (id, vehicle_type, model, body_type, manufacturer, price, created)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            mapObjectToRow(preparedStatement, car);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        final String sql = "UPDATE public.Cars " +
                "SET id = ?, vehicle_type = ?, model = ?, body_type = ?, manufacturer = ?, price = ?, created = ? " +
                "WHERE id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(8, car.getId());
            mapObjectToRow(preparedStatement, car);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        final String sql = "DELETE FROM public.Cars WHERE id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        final String sql = "DELETE FROM public.Cars";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private Car mapRowToObject(final ResultSet resultSet) {
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
    private void mapObjectToRow(final PreparedStatement preparedStatement, final Car car) {
        preparedStatement.setString(1, car.getId());
        preparedStatement.setString(2, "car");
        preparedStatement.setInt(3, car.getModel());
        preparedStatement.setString(4, car.getBodyType().name());
        preparedStatement.setString(5, car.getCarManufacturer().name());
        preparedStatement.setBigDecimal(6, car.getPrice());
        preparedStatement.setTimestamp(7, Timestamp.valueOf(car.getCreated()));
        preparedStatement.addBatch();
    }
}