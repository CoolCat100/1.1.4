package overridetech.jdbc.jpa.dao;

import overridetech.jdbc.jpa.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS User(id INT PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(25)," +
                        "last_name VARCHAR(30)," +
                        "age INT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS User");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (name, last_name, age) " +
                    "VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE " +
                    "id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from user");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public Connection getConnection(){
        return this.connection;
    }
}
