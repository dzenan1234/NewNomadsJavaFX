package com.example.newnomads;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL =
            "jdbc:mysql://newnomadsbaza-newnomadsapp.d.aivencloud.com:10105/newnomadsbaza" +
                    "?sslMode=REQUIRED" +
                    "&useUnicode=true" +
                    "&characterEncoding=utf8" +
                    "&serverTimezone=UTC";

    private static final String USER = "newnomadskorisnik";
    private static final String PASS = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (PASS == null || PASS.isBlank()) {
            throw new SQLException("DB_PASSWORD nije postavljena u .env fajlu!");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
