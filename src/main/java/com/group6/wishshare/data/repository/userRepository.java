package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.Util.DbManager;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.service.LoginException;

import java.sql.*;

public class userRepository {

  public void createUser(User user) throws LoginException {
    try {
      Connection connection = DbManager.getInstance().getConnection();
      String SQL =
          "INSERT INTO User (email, password, first_name, last_name, gender, birthdate) VALUES (?,"
              + " ?, ?, ?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, user.getEmail());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getFirstName());
      ps.setString(4, user.getLastName());
      ps.setString(5, user.getGender().name());
      ps.setString(6, String.valueOf(user.getBirthdate()));
      ps.executeUpdate();
      ResultSet ids = ps.getGeneratedKeys();
      ids.next();
    } catch (SQLException e) {
      throw new LoginException(e.getMessage());
    }
  }

  public User login(String email, String password) throws LoginException {
    try {
      Connection con = DbManager.getInstance().getConnection();
      String SQL = "SELECT user_id FROM user WHERE email=? AND password=?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, email);
      ps.setString(2, password);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("user_id");

        return new User.UserBuilder()
            .email(email)
            .password(password)
            .id(id)
            .build(); // TODO add more?
      } else {
        throw new LoginException("Not a valid user");
      }
    } catch (SQLException e) {
      throw new LoginException(e.getMessage());
    }
  }
}
