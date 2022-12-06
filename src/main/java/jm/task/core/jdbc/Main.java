package jm.task.core.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM table1";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
           Long userId = resultSet.getLong(1);
            String name = resultSet.getString(2);
            String lastname = resultSet.getString(3);
//            Byte age = resultSet.getByte(3);
            System.out.println(userId);
            System.out.println(name);
//            System.out.println(lastname);
//            System.out.println(age);

        }
        connection.close();
    }
}
