package com.example.newnomads;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/newnomads?serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "root!";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Registracija MySQL drivera
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
