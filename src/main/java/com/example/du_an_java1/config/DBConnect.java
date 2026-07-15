package com.example.du_an_java1.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=Quan_Ly_Ban_Quan;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true";

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "Buituantu207";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}