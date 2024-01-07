package org.santhu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connection = null;

    public static Connection getConnection()  {
        String URL = "jdbc:mysql://localhost:3306/password_manager?useSSL=false";
        String userName = "root";
        String password = "santhu";
        try {
            connection = DriverManager.getConnection(URL, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void closeConnection() {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
