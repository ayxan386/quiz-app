package com.aykhan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Deprecated
public class SQLConnection {
  private final static String url = "jdbc:postgresql://localhost:5432/quiz_storage";
  private final static String user = "postgres";
  private final static String pass = "ayxan123";

  private static Connection conn;

  public static Connection getConn() {
    if (conn == null) {
      try {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(url, user, pass);
      } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return conn;
  }
}
