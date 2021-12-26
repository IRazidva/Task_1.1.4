package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS user (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR (15)," +
                    "lastname VARCHAR (20)," +
                    "age INT )";
            statement.execute(sql);
            Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS user";
            statement.execute(sql);
            Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age)  {
        try {
            Connection connection = Util.getConnection();
            String SQL = "INSERT INTO user (name, lastname, age) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();

        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM user";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getNString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }
            Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUser;

    }

    @Override
    public void cleanUsersTable() {
        try {
            Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM user";
            statement.execute(sql);
            Util.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
