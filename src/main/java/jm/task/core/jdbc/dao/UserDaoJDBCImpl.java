package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS kata_test.users (id bigint(20) NOT NULL AUTO_INCREMENT, " +
                "name varchar(50) DEFAULT NULL, " +
                "lastname varchar(255) DEFAULT NULL, " +
                "age int(11) DEFAULT NULL, " +
                "PRIMARY KEY (id))";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropUserTableSql = "DROP TABLE IF EXISTS kata_test.users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(dropUserTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserSql = "INSERT INTO users(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUserSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeSql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeSql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAllUsersSql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllUsersSql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersTableSql = "TRUNCATE TABLE kata_test.users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(cleanUsersTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
