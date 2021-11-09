package com.group6.wishshare.data.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {
  private static DbManager instance;
  private Connection connection;
  private String url;
  private String user;
  private String password;

  /**
   * Create a connection to the database using properties defined in application.properties.
   *
   * @auther Mathias
   */
  private DbManager() {
    Properties properties = DBSelector.selectConnection();
    url = properties.getProperty("url");
    user = properties.getProperty("user");
    password = properties.getProperty("password");
    try {
      assert url != null;
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
  public static DbManager getInstance() {
    if (null == instance) {
      instance = new DbManager();
    } else {
      try {
        if (instance.getConnection().isClosed()) {
          instance = new DbManager();
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
