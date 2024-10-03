package com.github.waiversocial.database;

import com.github.waiversocial.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.*;

@Getter @Setter
public class Database {

    public static final Logger logger = LogManager.getLogger(Database.class);

    private final Connection databaseConnection;

    public Database(String dbName) {
        try {
            this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            Statement statement = databaseConnection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS messages (id INTEGER PRIMARY KEY, user TEXT, message TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT)");
            logger.info("Connection to SQLite has been established.");
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database", e);
        }
    }

    public void save() {
        try {
            databaseConnection.close();
        } catch (SQLException e) {
            logger.error("Could not close database connection", e);
        }
    }

    public Optional<User> getUser(String username) {
        try {
            PreparedStatement statement = databaseConnection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(resultSet.getString("username"), resultSet.getString("password")));
            }
        } catch (SQLException e) {
            logger.error("Could not get user", e);
        }
        return Optional.empty();
    }

}