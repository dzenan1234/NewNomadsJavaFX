package com.example.newnomads;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final String URL =
            "jdbc:mysql://newnomadsbaza-newnomadsapp.d.aivencloud.com:10105/newnomadsbaza" +
                    "?sslMode=REQUIRED" +
                    "&useUnicode=true" +
                    "&characterEncoding=utf8" +
                    "&serverTimezone=UTC";

    private static final String USER = "newnomadskorisnik";
    private static final String PASS = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (PASS == null || PASS.isBlank()) {
            throw new SQLException("DB_PASSWORD environment variable nije postavljena!");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
