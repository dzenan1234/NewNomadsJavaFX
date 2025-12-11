package com.example.newnomads;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {

    public static void main(String[] args) {
        try (Connection conn = DB.getConnection()) {
            System.out.println("✅ KONEKCIJA USPJEŠNA!");
            System.out.println("URL: " + conn.getMetaData().getURL());
            System.out.println("User: " + conn.getMetaData().getUserName());
            System.out.println("DB: " + conn.getCatalog());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("❌ GREŠKA PRI KONEKCIJI:");
            e.printStackTrace();
        }
    }
}
