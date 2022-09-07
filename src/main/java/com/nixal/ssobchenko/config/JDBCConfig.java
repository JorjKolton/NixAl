package com.nixal.ssobchenko.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class JDBCConfig {
    private static final String URL = "postgres://zsvuuseouarbfy:a10777c96b9ac8426c04684acc0c392003d978a2ee99f2f2b54ab0ba5ea9d1fb@ec2-54-204-241-136.compute-1.amazonaws.com:5432/d1miis3dfaq9pt";
    private static final String USER = "zsvuuseouarbfy";
    private static final String PASSWORD = "a10777c96b9ac8426c04684acc0c392003d978a2ee99f2f2b54ab0ba5ea9d1fb";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}