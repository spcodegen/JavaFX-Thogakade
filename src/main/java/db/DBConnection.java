package db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static DBConnection instance;

    @Getter
    private Connection connection;

    private DBConnection() throws SQLException {
        String URL="jdbc:mysql://localhost:3306/thogakade";
        String user="root";
        String pass="1234";
        connection = DriverManager.getConnection(URL, user, pass);
    }

    public static DBConnection getInstance() throws SQLException {
        return instance == null ? instance = new DBConnection() : instance;
    }
}
