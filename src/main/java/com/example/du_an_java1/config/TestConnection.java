package com.example.du_an_java1.config;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DBConnect.getConnection();

        if (conn != null) {
            System.out.println("Kết nối SQL Server thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
