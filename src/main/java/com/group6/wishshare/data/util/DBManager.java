package com.group6.wishshare.data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

  private static DBManager instance;
  private Connection connection;

  /**
   * Create a connection to the database using properties defined in application.properties.
   *
   * @auther Mathias
   */
  private DBManager() {
    Properties properties = DBSelector.selectConnection();
    String url = properties.getProperty("url");
    String user = properties.getProperty("user");
    String password = properties.getProperty("password");
    try {
      this.connection = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns a new instance of DbManager using singleton pattern.
   *
   * @auther Mathias
   */
  public static DBManager getInstance() {
    if (null == instance) {
      instance = new DBManager();
    } else {
      try {
        if (instance.getConnection().isClosed()) {
          instance = new DBManager();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return instance;
  }

  /**
   * Returns the connection of the instance.
   *
   * @auther Mathias
   */
  public Connection getConnection() {
    return connection;
  }
}
