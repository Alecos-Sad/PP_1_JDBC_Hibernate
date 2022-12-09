package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private final Connection connection = Util.getConnection();
    private static final String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS kata_test.users (id bigint(20) NOT NULL AUTO_INCREMENT, " +
            "name varchar(50) DEFAULT NULL, " +
            "lastname varchar(255) DEFAULT NULL, " +
            "age int(11) DEFAULT NULL, " +
            "PRIMARY KEY (id))";
    private static final String DROP_USER_TABLE_SQL = "DROP TABLE IF EXISTS kata_test.users";
    private static final String SAVE_USER_SQL = "INSERT INTO users(name, lastname, age) VALUES(?,?,?)";
    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT id, name, lastname, age FROM users";
    private static final String CLEAN_USER_TABLE_SQL = "TRUNCATE TABLE kata_test.users";


    private UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERS_TABLE_SQL)) {
            preparedStatement.execute(CREATE_USERS_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DROP_USER_TABLE_SQL)) {
            preparedStatement.execute(DROP_USER_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery(GET_ALL_USERS_SQL);
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_SQL)) {
            preparedStatement.execute(CLEAN_USER_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }
}
