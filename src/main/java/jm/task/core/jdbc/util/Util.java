package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    static {
        loadDriver();
    }

    private Util() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    PropertyUtil.get(URL_KEY),
                    PropertyUtil.get(USERNAME_KEY),
                    PropertyUtil.get(PASSWORD_KEY));
            if (!connection.isClosed()){
                System.out.println("Соединение с БД установленно!");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка соединения!");
        }
        return connection;
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
