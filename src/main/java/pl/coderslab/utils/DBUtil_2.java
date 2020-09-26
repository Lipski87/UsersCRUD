package pl.coderslab.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil_2 {

    public static String DB_URL = "jdbc:mysql://localhost:3306/workshop2?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
    public static String DB_USER = "root";
    public static String DB_PASS = "coderslab";

    public static Connection connect() throws SQLException {

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
