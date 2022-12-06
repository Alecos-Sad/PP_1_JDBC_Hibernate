package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

  public static Connection getConnection() {

    String url = "jdbc:mysql://localhost:3306/kata_test";
    String username = "root";
    String password = "root";

    Connection connection = null;
    try {
      connection = DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }
}
