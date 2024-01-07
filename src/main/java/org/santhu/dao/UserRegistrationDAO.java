package org.santhu.dao;

import org.mindrot.jbcrypt.BCrypt;
import org.santhu.db.MyConnection;
import org.santhu.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegistrationDAO {
    public static int register(User user) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into user_login values(default, ?, ?)");
        statement.setString(1, user.getUserName());
        statement.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return statement.executeUpdate();
    }

    public static boolean isExists(String userName) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("select username from user_login");
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            if (set.getString(1).equals(userName)) return true;
        }
        return false;
    }

    public static String isUserValid(String userName, String password) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT username, password FROM user_login WHERE username = ?");
        statement.setString(1, userName);
        ResultSet set = statement.executeQuery();
        if(set.next()) {
            return BCrypt.checkpw(password, set.getString("password")) ? "true" : "false";
        }
        return "user not found";
    }
}
