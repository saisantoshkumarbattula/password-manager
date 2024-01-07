package org.santhu.dao;

import org.santhu.db.MyConnection;
import org.santhu.model.User_Passwords;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserOperationsDAO {
    public static List<User_Passwords> getAllSavedPasswords(String userName) throws SQLException {
        Connection connection = MyConnection.getConnection();
        List<User_Passwords> list = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from user_passwords where user_id = ?");
        statement.setInt(1, getUserId(userName));
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int count = resultSet.getInt(1);
            int user_id = resultSet.getInt(2);
            String website_name = resultSet.getString(3);
            String website_username = resultSet.getString(4);
            String website_password = resultSet.getString(5);
            list.add(new User_Passwords(count, user_id, website_name, website_username, website_password));
        }
        return list;
    }

    public static int savePassword(User_Passwords userPasswords) throws SQLException {
        Connection connection = MyConnection.getConnection();
        String query = "INSERT INTO user_passwords (user_id, website_name, website_username, website_password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userPasswords.getUser_id());
            statement.setString(2, userPasswords.getWebsite_name());
            statement.setString(3, userPasswords.getWebsite_username());
            statement.setString(4, userPasswords.getWebsite_password());
            return statement.executeUpdate();
        }
    }

    public static int getUserId(String userName) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM user_login WHERE username = ?");
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("user_id");
        } else {
            return -1;
        }
    }

    public static int deletePassword(int userId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        String query = "DELETE FROM user_passwords WHERE pass_count = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            return statement.executeUpdate();
        }
    }
}